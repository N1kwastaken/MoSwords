package net.n1kwastaken.moswords.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.n1kwastaken.moswords.MoSwords;
import net.n1kwastaken.moswords.mixin.RecipeBookWidgetInvoker;

@Environment(EnvType.CLIENT)
public class BiggerCraftingScreen extends HandledScreen<BiggerCraftingScreenHandler> implements RecipeBookProvider {
    private static final Identifier TEXTURE = new Identifier(MoSwords.MOD_ID, "textures/gui/container/bigger_crafting_table.png");
    private static final Text TOGGLE_ALL_RECIPES_TEXT = Text.literal("Showing All");
    private static final Text TOGGLE_BIGGER_RECIPES_TEXT = Text.literal("Showing Only Bigger Recipes");
    private static final ButtonTextures BIGGER_ONLY_FILTER_BUTTON_TEXTURES = new ButtonTextures(
            new Identifier(MoSwords.MOD_ID, "recipe_book/bigger_only_filter_enabled"),
            new Identifier(MoSwords.MOD_ID, "recipe_book/bigger_only_filter_disabled"),
            new Identifier(MoSwords.MOD_ID, "recipe_book/bigger_only_filter_enabled_highlighted"),
            new Identifier(MoSwords.MOD_ID, "recipe_book/bigger_only_filter_disabled_highlighted"));
    private final RecipeBookWidget recipeBook = new RecipeBookWidget();
    protected ToggleButtonWidget toggleOnlyBiggerRecipesButton;
    private boolean narrow;

    public BiggerCraftingScreen(BiggerCraftingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 184;
        this.playerInventoryTitleY = 90;
        this.titleX = 20;
    }

    @Override
    protected void init() {
        super.init();
        this.narrow = this.width < 379;
        this.recipeBook.initialize(this.width, this.height, this.client, this.narrow, this.handler);
        this.x = this.recipeBook.findLeftEdge(this.width, this.backgroundWidth);
        this.addDrawableChild(new TexturedButtonWidget(this.x + 131, this.height / 2 - 79, 20, 18, RecipeBookWidget.BUTTON_TEXTURES, button -> {
            this.recipeBook.toggleOpen();
            this.toggleOnlyBiggerRecipesButton.visible = this.recipeBook.isOpen();
            this.x = this.recipeBook.findLeftEdge(this.width, this.backgroundWidth);
            button.setPosition(this.x + 131, this.height / 2 - 79);
        }));
        this.toggleOnlyBiggerRecipesButton = new ToggleButtonWidget((this.width - 147) / 2 + 24, (this.height - 166) / 2 + 137, 27, 17, false);
        this.toggleOnlyBiggerRecipesButton.setTextures(BIGGER_ONLY_FILTER_BUTTON_TEXTURES);
        this.toggleOnlyBiggerRecipesButton.visible = this.recipeBook.isOpen();
        this.updateToggleBiggerRecipesTooltip();

        this.addDrawableChild(this.toggleOnlyBiggerRecipesButton);
        this.addSelectableChild(this.recipeBook);
        this.setInitialFocus(this.recipeBook);
    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
        this.recipeBook.update();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.recipeBook.isOpen() && this.narrow) {
            this.renderBackground(context, mouseX, mouseY, delta);
            this.recipeBook.render(context, mouseX, mouseY, delta);
        } else {
            super.render(context, mouseX, mouseY, delta);
            this.recipeBook.render(context, mouseX, mouseY, delta);
            this.toggleOnlyBiggerRecipesButton.render(context, mouseX, mouseY, delta);
            this.recipeBook.drawGhostSlots(context, this.x, this.y, true, delta);
        }
        this.drawMouseoverTooltip(context, mouseX, mouseY);
        this.recipeBook.drawTooltip(context, this.x, this.y, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = this.x;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    protected boolean isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY) {
        return (!this.narrow || !this.recipeBook.isOpen()) && super.isPointWithinBounds(x, y, width, height, pointX, pointY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(this.recipeBook);
            return true;
        }
        if (this.toggleOnlyBiggerRecipesButton.mouseClicked(mouseX, mouseY, button)) {
            this.handler.onlyBiggerRecipes = !this.handler.onlyBiggerRecipes;
            this.toggleOnlyBiggerRecipesButton.setToggled(this.handler.onlyBiggerRecipes);
//            MoSwords.LOGGER.info("" + this.onlyBiggerRecipes);
            this.updateToggleBiggerRecipesTooltip();
//                this.sendBookDataPacket();
            (((RecipeBookWidgetInvoker) this.recipeBook)).invokeRefreshResults(false);
            return true;
        }
        if (this.narrow && this.recipeBook.isOpen()) {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void updateToggleBiggerRecipesTooltip() {
        this.toggleOnlyBiggerRecipesButton.setTooltip(this.handler.onlyBiggerRecipes ? Tooltip.of(TOGGLE_BIGGER_RECIPES_TEXT) : Tooltip.of(TOGGLE_ALL_RECIPES_TEXT));
    }

    @Override
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        boolean outsideBounds = mouseX < (double) left || mouseY < (double) top || mouseX >= (double) (left + this.backgroundWidth) || mouseY >= (double) (top + this.backgroundHeight);
        return this.recipeBook.isClickOutsideBounds(mouseX, mouseY, this.x, this.y, this.backgroundWidth, this.backgroundHeight, button) && outsideBounds;
    }

    @Override
    protected void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType) {
        super.onMouseClick(slot, slotId, button, actionType);
        this.recipeBook.slotClicked(slot);
    }

    @Override
    public void refreshRecipeBook() {
        this.recipeBook.refresh();
    }

    @Override
    public RecipeBookWidget getRecipeBookWidget() {
        return this.recipeBook;
    }
}
