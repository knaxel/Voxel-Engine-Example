#version 440

in float textureID;
in vec2 fuv_coord;
in vec2 fuv_scale;
in vec3 f_norm;
in vec3 sunDirection;

out vec4 out_Color;

uniform sampler2D diffuse_texture;
uniform vec3 sunPosition;
uniform vec3 sunColor;
uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;

uniform int wireframe;


void main(void) {

	
	
	// normalize((transformationMatrix *vec4(sunDirection,1)).xyz))
	
	float diffuseIntensity = max(dot(f_norm, sunDirection ), 0.1);
	vec3 diffuseColor = sunColor * diffuseIntensity;

	vec2 coord = vec2(textureID/16.0, mod(textureID,16)) + mod( fuv_scale*fuv_coord/16.0, 1.0/16.0);
	
	if (wireframe == 1) {
		out_Color = vec4(1, 0, 0, 1);
		return;
	}

	out_Color = texture(diffuse_texture, coord);

}
