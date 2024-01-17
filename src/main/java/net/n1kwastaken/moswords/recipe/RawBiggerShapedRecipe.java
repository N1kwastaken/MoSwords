package net.n1kwastaken.moswords.recipe;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.chars.CharArraySet;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public record RawBiggerShapedRecipe(int width, int height, DefaultedList<Ingredient> ingredients, Optional<RawBiggerShapedRecipe.Data> data) {
    public static final MapCodec<RawBiggerShapedRecipe> CODEC = RawBiggerShapedRecipe.Data.CODEC.flatXmap(
            RawBiggerShapedRecipe::fromData, recipe -> recipe.data().map(DataResult::success).orElseGet(() -> DataResult.error(() -> "Cannot encode unpacked recipe")));

    private static DataResult<RawBiggerShapedRecipe> fromData(RawBiggerShapedRecipe.Data data) {
        String[] pattern = removePadding(data.pattern);
        int width = pattern[0].length();
        int height = pattern.length;
        DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(width * height, Ingredient.EMPTY);
        CharArraySet keys = new CharArraySet(data.key.keySet());
        for (int rowIndex = 0; rowIndex < height; ++rowIndex) {
            String row = pattern[rowIndex];
            for (int columnIndex = 0; columnIndex < width; ++columnIndex) {
                char c = row.charAt(columnIndex);
                Ingredient ingredient = c == ' ' ? Ingredient.EMPTY : data.key.get(c);
                if (ingredient == null) {
                    return DataResult.error(() -> "Pattern references symbol '" + c + "' but it's not defined in the key");
                }
                keys.remove(c);
                ingredients.set(columnIndex + width * rowIndex, ingredient);
            }
        }
        if (!keys.isEmpty()) {
            return DataResult.error(() -> "Key defines symbols that aren't used in pattern: " + keys);
        }
        return DataResult.success(new RawBiggerShapedRecipe(width, height, ingredients, Optional.of(data)));
    }

    @VisibleForTesting
    static String[] removePadding(List<String> pattern) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;
        for (int rowIndex = 0; rowIndex < pattern.size(); ++rowIndex) {
            String row = pattern.get(rowIndex);
            i = Math.min(i, findFirstSymbol(row));
            int n = findLastSymbol(row);
            j = Math.max(j, n);
            if (n < 0) {
                if (k == rowIndex) {
                    ++k;
                }
                ++l;
                continue;
            }
            l = 0;
        }
        if (pattern.size() == l) {
            return new String[0];
        }
        String[] strings = new String[pattern.size() - l - k];
        for (int o = 0; o < strings.length; ++o) {
            strings[o] = pattern.get(o + k).substring(i, j + 1);
        }
        return strings;
    }


    private static int findFirstSymbol(String line) {
        int firstIndex = 0;
        while (firstIndex < line.length() && line.charAt(firstIndex) == ' ') {
            ++firstIndex;
        }
        return firstIndex;
    }

    private static int findLastSymbol(String line) {
        int lastIndex = line.length() - 1;
        while (lastIndex >= 0 && line.charAt(lastIndex) == ' ') {
            --lastIndex;
        }
        return lastIndex;
    }

    public boolean matches(RecipeInputInventory inventory) {
        for (int i = 0; i <= inventory.getWidth() - this.width; ++i) {
            for (int j = 0; j <= inventory.getHeight() - this.height; ++j) {
                if (this.matches(inventory, i, j, true)) {
                    return true;
                }
                if (this.matches(inventory, i, j, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean matches(RecipeInputInventory inventory, int offsetX, int offsetY, boolean flipped) {
        for (int i = 0; i < inventory.getWidth(); ++i) {
            for (int j = 0; j < inventory.getHeight(); ++j) {
                int k = i - offsetX;
                int l = j - offsetY;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
                    ingredient = flipped ? this.ingredients.get(this.width - k - 1 + l * this.width) : this.ingredients.get(k + l * this.width);
                }
                if (!ingredient.test(inventory.getStack(i + j * inventory.getWidth()))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void writeToBuf(PacketByteBuf buf) {
        buf.writeVarInt(this.width);
        buf.writeVarInt(this.height);
        for (Ingredient ingredient : this.ingredients) {
            ingredient.write(buf);
        }
    }

    public static RawBiggerShapedRecipe readFromBuf(PacketByteBuf buf) {
        int width = buf.readVarInt();
        int height = buf.readVarInt();
        DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(width * height, Ingredient.EMPTY);
        ingredients.replaceAll(ingredient -> Ingredient.fromPacket(buf));
        return new RawBiggerShapedRecipe(width, height, ingredients, Optional.empty());
    }


    public record Data(Map<Character, Ingredient> key, List<String> pattern) {
        private static final int MAX_WIDTH_AND_HEIGHT = 4;
        private static final Codec<List<String>> PATTERN_CODEC = Codec.STRING.listOf().comapFlatMap(pattern -> {
            if (pattern.size() > MAX_WIDTH_AND_HEIGHT) {
                return DataResult.error(() -> "Invalid pattern: too many rows, 3 is maximum");
            }
            if (pattern.isEmpty()) {
                return DataResult.error(() -> "Invalid pattern: empty pattern not allowed");
            }
            int firstRowLength = pattern.get(0).length();
            for (String row : pattern) {
                if (row.length() > MAX_WIDTH_AND_HEIGHT) {
                    return DataResult.error(() -> "Invalid pattern: too many columns, 3 is maximum");
                }

                if (firstRowLength != row.length()) {
                    return DataResult.error(() -> "Invalid pattern: each row must be the same width");
                }
            }
            return DataResult.success(pattern);
        }, Function.identity());
        private static final Codec<Character> KEY_ENTRY_CODEC = Codec.STRING.comapFlatMap(keyEntry -> {
            if (keyEntry.length() != 1) {
                return DataResult.error(() -> "Invalid key entry: '" + keyEntry + "' is an invalid symbol (must be 1 character only).");
            }
            if (" ".equals(keyEntry)) {
                return DataResult.error(() -> "Invalid key entry: ' ' is a reserved symbol.");
            }
            return DataResult.success(keyEntry.charAt(0));
        }, String::valueOf);
        public static final MapCodec<RawBiggerShapedRecipe.Data> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codecs.strictUnboundedMap(
                        KEY_ENTRY_CODEC, Ingredient.DISALLOW_EMPTY_CODEC).fieldOf("key").forGetter(data -> data.key),
                        PATTERN_CODEC.fieldOf("pattern").forGetter(data -> data.pattern))
                .apply(instance, RawBiggerShapedRecipe.Data::new));
    }
}
