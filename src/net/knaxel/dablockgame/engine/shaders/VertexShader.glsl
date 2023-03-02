#version 440

in int attrib1;
in int attrib2;

// attrib1 :
// x      y    z     w     h   t-id ?
// 00000 00000 00000 0000 0000 00000000 0 //
// attrib 2:
// uv norm norm norm dmg
// 000 0000 0000 0000 0000 0000 0000 00000
// 000 0000 0000 1000 0000 0000 0000 00000
// 000 0000 1111 0000 0000 0000 0000 00000
out float textureID;
out vec2 fuv_coord;
out vec2 fuv_scale;
out vec3 f_norm;
out vec3 sunDirection;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 sunPosition;


const vec2 uvlist[4] = {
		vec2(0.0f,0.0f),
		vec2(0.0f,1.0f),
		vec2(1.0f,1.0f),
		vec2(1.0f,0.0f),
};


void main(void){
	vec3 position = vec3(float((attrib1 >>27  & 0x1f)),float((attrib1 >>22 & 0x1f)),float((attrib1 >> 17 & 0x1f )));
	vec4 relativePos  =transformationMatrix * vec4(position,1.0);

	textureID = float(attrib1 >> 1&0xff);
	
	fuv_scale = vec2(float((attrib1>>13 & 0xf)) +1 ,float((attrib1>>9 & 0xf))+1 );
	fuv_coord = uvlist[attrib2>> 30 & 3] ;
	
	float dir1 = float((attrib2 & 0x1e000000 ))-1; 
	float dir2 =  float((attrib2 & 0x1e00000  ))-1;
	float dir3 =  float(( attrib2 & 0x1e0000 ))-1 ;
	f_norm = normalize(vec3(  dir1,dir2, dir3 ));
	
	sunDirection  =normalize( vec4(sunPosition,1) - relativePos).xyz;

	gl_Position = projectionMatrix * viewMatrix * relativePos;

}
