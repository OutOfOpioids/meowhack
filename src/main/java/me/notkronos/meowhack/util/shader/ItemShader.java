package me.notkronos.meowhack.util.shader;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.module.render.Shader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class ItemShader extends FramebufferShader
{
    public float mix = 0.0f;
    public float alpha = 1.0f;
    public ItemShader(String fragmentShader) {
        super(fragmentShader);
    }

    @Override
    public void setupUniforms()
    {
        setupUniform("texture");
        setupUniform("texelSize");
        setupUniform("radius");
        setupUniform("divider");
        setupUniform("radius");
        setupUniform("maxSample");
        setupUniform("mixFactor");
        setupUniform("minAlpha");
        setupUniform("firstGradientColor");
        setupUniform("secondGradientColor");
        setupUniform("speed");
        setupUniform("stretch");
        setupUniform("dimensions");
        setupUniform("time");
    }

    @Override
    public void updateUniforms()
    {
        GL20.glUniform1i(getUniform("texture"), -1);
        GL20.glUniform2f(getUniform("texelSize"), 1F / mc.displayWidth * (radius * quality), 1F / mc.displayHeight * (radius * quality));
        GL20.glUniform1f(getUniform("radius"), radius);
        GL20.glUniform1f(getUniform("divider"), 140F);
        GL20.glUniform1f(getUniform("maxSample"), 10F);
        GL20.glUniform1f(getUniform("mixFactor"), mix);
        GL20.glUniform1f(getUniform("minAlpha"), alpha);
        GL20.glUniform3f(getUniform("firstGradientColor"), Shader.fGradRed.value / 255f, Shader.fGradGreen.value / 255f, Shader.fGradBlue.value / 255f);
        GL20.glUniform3f(getUniform("secondGradientColor"), Shader.sGradRed.value / 255f, Shader.sGradGreen.value / 255f, Shader.sGradBlue.value / 255f);
        GL20.glUniform1f(getUniform("speed"), Shader.speed.value);
        GL20.glUniform1f(getUniform("stretch"), Shader.stretch.value);
        GL20.glUniform2f(getUniform("dimensions"), mc.displayWidth, mc.displayHeight);
        float t = (System.currentTimeMillis() - Meowhack.INSTANCE.initTime) / 1000F;
        GL20.glUniform1f(getUniform("time"), t);
    }

}
