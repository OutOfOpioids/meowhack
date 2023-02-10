package me.notkronos.meowhack.util.shader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class ItemShader extends FramebufferShader
{
    public float mix = 0.0f;
    public float alpha = 1.0f;

    public ItemShader() {

        super("itemglow.frag");
    }

    @Override
    public void setupUniforms()
    {
        setupUniform("texture");
        setupUniform("texelSize");
        setupUniform("color");
        setupUniform("divider");
        setupUniform("radius");
        setupUniform("maxSample");
        setupUniform("dimensions");
        setupUniform("mixFactor");
        setupUniform("minAlpha");
        setupUniform("image");
    }

    @Override
    public void updateUniforms()
    {
        GL20.glUniform1i(getUniform("texture"), -1);
        GL20.glUniform2f(getUniform("texelSize"), 1F / mc.displayWidth * (radius * quality), 1F / mc.displayHeight * (radius * quality));
        GL20.glUniform3f(getUniform("color"), red, green, blue);
        GL20.glUniform1f(getUniform("divider"), 140F);
        GL20.glUniform1f(getUniform("radius"), radius);
        GL20.glUniform1f(getUniform("maxSample"), 10F);
        GL20.glUniform2f(getUniform("dimensions"), mc.displayWidth, mc.displayHeight);
        GL20.glUniform1f(getUniform("mixFactor"), mix);
        GL20.glUniform1f(getUniform("minAlpha"), alpha);
        GL13.glActiveTexture(GL13.GL_TEXTURE8);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 8);
        GL20.glUniform1i(getUniform("image"), 0);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
    }

}
