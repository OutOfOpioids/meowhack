#version 120

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

void main() {
    vec4 centerCol = texture2D(texture, gl_TexCoord[0].xy);

    float alpha = 0;

    if (centerCol.a != 0) {
        gl_FragColor = vec4(mix(centerCol.rgb, firstGradientColor, mixFactor), centerCol.a);
        // gl_FragColor = vec4(centerCol.rgb, centerCol.a);
    } else {
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
        gl_FragColor = vec4(secondGradientColor, alpha);
    }
}