package org.shadowmaster435.mixin;

import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import org.shadowmaster435.ParticlePacks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(SpriteAtlasTexture.class)
public abstract class SpriteAtlasTextureMixin {


    @Shadow @Final @Deprecated public static Identifier PARTICLE_ATLAS_TEXTURE;

    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(Identifier id, CallbackInfo ci) {
        if (Objects.equals(id.toString(), PARTICLE_ATLAS_TEXTURE.toString())) {
            ParticlePacks.provider = (SpriteAtlasTexture)(Object) this;
        }

    }

}
