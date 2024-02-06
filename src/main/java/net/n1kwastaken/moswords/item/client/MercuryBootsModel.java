package net.n1kwastaken.moswords.item.client;

import net.minecraft.util.Identifier;
import net.n1kwastaken.moswords.MoSwords;
import net.n1kwastaken.moswords.item.custom.MercuryBootsItem;
import software.bernie.geckolib.model.GeoModel;

public class MercuryBootsModel extends GeoModel<MercuryBootsItem> {
    @Override
    public Identifier getModelResource(MercuryBootsItem animatable) {
        return new Identifier(MoSwords.MOD_ID, "geo/mercury_boots.geo.json");
    }

    @Override
    public Identifier getTextureResource(MercuryBootsItem animatable) {
        return new Identifier(MoSwords.MOD_ID, "textures/armor/mercury_boots.png");
    }

    @Override
    public Identifier getAnimationResource(MercuryBootsItem animatable) {
        return new Identifier(MoSwords.MOD_ID, "animations/mercury_boots.animation.json");
    }
}
