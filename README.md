# Voxel Chunk Greedy Meshing Example with ECS

example is 6x6x6 chunks that are 16x16x16 voxels (mesh is aware of other chunks): 

<div  align="center" >
<a href="https://github.com/knaxel/Voxel-Chunk-Greedy-Meshing-Example-with-ECS/blob/main/git/1.png"><img src="https://github.com/knaxel/Voxel-Chunk-Greedy-Meshing-Example-with-ECS/blob/main/git/1.png" width="250" ></a>

<a href="https://github.com/knaxel/Voxel-Chunk-Greedy-Meshing-Example-with-ECS/blob/main/git/2.png">
  <img src="https://github.com/knaxel/Voxel-Chunk-Greedy-Meshing-Example-with-ECS/blob/main/git/2.png"  width="250" ></a>

<a href="https://github.com/knaxel/Voxel-Chunk-Greedy-Meshing-Example-with-ECS/blob/main/git/3.png">
  <img src="https://github.com/knaxel/Voxel-Chunk-Greedy-Meshing-Example-with-ECS/blob/main/git/3.png"  width="250" ></a>
  </div>
  
![test](https://github.com/knaxel/Voxel-Engine-Example/blob/main/gif.gif?raw=true=250x250)

# Features :
- E.C.S. > Entity Component System Game Engine [Yoiked Random ideas from DivotKey](https://github.com/divotkey/ecs)
- Multi threaded [Greedy Meshing](https://0fps.net/2012/06/30/meshing-in-a-minecraft-game/) of 16x16x16 chunk data
- Data compression in mesh VBO's [the idea giver guy](https://www.youtube.com/watch?v=VQuN1RMEr1c)
- Per Block type Textures (from Minecraft Game) [minecraft.net (dont sue me plx its an old asset) ](https://www.minecraft.net)

# Usage 
Its an Eclipse project.
- Download Eclipse if you dont have it (for Java developers)
- Download the Dependencies below
- Import the project folder into eclipse ( this repo folder)
- Right Click Project > Properties > Java Build Path 
   - Setup LWJGL3 paths in a library by replacing the LWJGL3 if its missing
   - drag n drop slick-utils3 and command-lang3 into "lib/" folder or adjust path urself.

# Controls
- w          -> move fowards
- s          -> move backwards
- left_shift -> speed up
- e          -> toggle wireframe mesh
- escape     -> close

# Dependencies 
- Commons-lang3 [3.12.0 used](https://commons.apache.org/proper/commons-lang/)
- slick-util3 [used the fork floating around](http://forum.lwjgl.org/index.php?topic=5799.0)
- [LWJGL3](https://www.lwjgl.org)


# Note 
I am not working on this anymore, wont be. It's a garbage way of doing things IMO.
Flaws : 
- "slow"
- - too many meshes
- - too many single blocks in memory, generalize the voxel data and not just the mesh, especially air....
- - greedy meshing.... slow. Sorry, I said it. Still might use it unless I get smarter.
- - OpenGL (depr. macOs)
- ugly texture repeat because no softening on atlas mapping
- Didn't follow ECS all the way through and made objects.
