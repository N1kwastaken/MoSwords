package net.n1kwastaken.moswords.item.client;

import net.n1kwastaken.moswords.item.custom.MercuryBootsItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class MercuryBootsRenderer extends GeoArmorRenderer<MercuryBootsItem> {
    public MercuryBootsRenderer() {
        super(new MercuryBootsModel());
    }
}
