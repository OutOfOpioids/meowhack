#shader vert
#version 120

void main(void) {
    //Map gl_MultiTexCoord0 to index zero of gl_TexCoord
    gl_TexCoord[0] = gl_MultiTexCoord0;

    //Calculate position by multiplying model, view and projection matrix by the vertex vector
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}

#shader frag
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

    gl_FragColor = vec4(mix(centerCol.rgb, finalColor, mixFactor), centerCol.a);
}