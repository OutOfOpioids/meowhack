//https://www.shadertoy.com/view/tdSBzK
#version 120

#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D texture;
uniform vec2 texelSize;
uniform float radius;
uniform float divider;
uniform float maxSample;
uniform float mixFactor;
uniform float minAlpha;
uniform vec3 firstGradientColor;
uniform vec3 secondGradientColor;
uniform float speed;
uniform float stretch;

uniform vec2 dimensions;
uniform float time;

float scrollAndStretch(float val, float st, float revolutionsPerSecond) {
    float s = revolutionsPerSecond * dimensions.x;
    return mod((val + s * time) * st, dimensions.x);
}

vec3 gradient(vec3 fCol, vec3 sCol, float x) {
    return mix(fCol, sCol, abs(1.0 - 2.0 * x / dimensions.x) * 0.35);
}

void main() {
    vec4 centerCol = texture2D(texture, gl_TexCoord[0].xy);
    vec3 finalColor = gradient(firstGradientColor, secondGradientColor, scrollAndStretch(gl_FragCoord.x, stretch, speed));

    if (centerCol.a != 0) {
        gl_FragColor = vec4(mix(centerCol.rgb, finalColor, mixFactor), centerCol.a);
    } else {
        float alpha = 0;
        for (float x = -radius; x < radius; x++) {
            for (float y = -radius; y < radius; y++) {
                vec4 currentColor = texture2D(texture, gl_TexCoord[0].xy + vec2(texelSize.x * x, texelSize.y * y));

                /*if (blur) {
                    currentColor = blur13(texture, gl_TexCoord[0].xy + vec2(texelSize.x * x, texelSize.y * y), dimensions, vec2(3, 3));
                }*/

                if (currentColor.a != 0)
                alpha += divider > 0 ? max(0, (maxSample - distance(vec2(x, y), vec2(0))) / divider) : 1;
                alpha *= minAlpha;
            }
        }
        gl_FragColor = vec4(finalColor, alpha);
    }
}
