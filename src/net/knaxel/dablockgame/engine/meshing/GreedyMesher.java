package net.knaxel.dablockgame.engine.meshing;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang3.ArrayUtils;

import net.knaxel.dablockgame.engine.resource.Resource;
import net.knaxel.dablockgame.engine.utils.vector.Vector3i;
import net.knaxel.dablockgame.world.Chunk;
import net.knaxel.dablockgame.world.World;
import net.knaxel.dablockgame.world.block.BlockFace;
import net.knaxel.dablockgame.world.block.BlockType;

public class GreedyMesher implements Runnable {

	private static final int SOUTH = 0;
	private static final int NORTH = 1;
	private static final int EAST = 2;
	private static final int WEST = 3;
	private static final int UP = 4;
	private static final int DOWN = 5;

	private Queue<Vector3i> jobs;
	private Queue<TerrainMesh> finished;

	private World world_ref;

	public GreedyMesher(World world) {
		this.world_ref = world;
		jobs = new LinkedList<Vector3i>();
		finished = new LinkedList<TerrainMesh>();
	}

	public void addJob(Vector3i chunk_loc) {
		if (!jobs.contains(chunk_loc)) {
			jobs.add(chunk_loc);
		} else {
			System.out.println("tried to add existing chunk to queue");

		}
	}

	public TerrainMesh popMesh() {
		return finished.remove();
	}

	public boolean hasJob() {
		return jobs.size() > 0;
	}

	public boolean hasMesh() {
		return finished.size() > 0;
	}

	public void greedy() {

		Vector3i chunk_loc = jobs.peek();
		Chunk chunk = world_ref.getChunk(chunk_loc.x,chunk_loc.y,chunk_loc.z);
		int layer = chunk_loc.y;

//		float[] vertices = {};
//		int[] indices = {};
//		float[] normals = {};
//		float[] uv = {};
//		float[] texinfo = {};
		int[] attrib1 = {};
		int[] attrib2 = {};
		int[] indices = {};
		
		int[] dims = { Chunk.CHUNK_SIZE, Chunk.CHUNK_SIZE, Chunk.CHUNK_SIZE };
		// Cache buffer internally
		BlockFace v1, v2;

		BlockFace[] mask;

		int pass = 0;
		for (boolean backFace = true, b = false; b != backFace; backFace = backFace && b, b = !b) {
			// Sweep over 3-axes
			for (int d = 0; d < 3; ++d) {
				int i, j, k, l, w, h, side = 0, u = (d + 1) % 3, v = (d + 2) % 3;
				int[] x = { 0, 0, 0 }, q = { 0, 0, 0 };

				mask = new BlockFace[dims[u] * dims[v]];

				x[0] = 0;
				x[1] = 0;
				x[2] = 0;
				q[0] = 0;
				q[1] = 0;
				q[2] = 0;
				q[d] = 1;
				if (d == 0) {
					side = backFace ? WEST : EAST;
				} else if (d == 1) {
					side = backFace ? DOWN : UP;
				} else if (d == 2) {
					side = backFace ? SOUTH : NORTH;
				}

				for (x[d] = -1; x[d] < dims[d];) {
					// Compute mask
					int n = 0;
					for (x[v] = 0; x[v] < dims[v]; ++x[v])
						for (x[u] = 0; x[u] < dims[u]; ++x[u]) {

							v1 = world_ref.f(x[0] +  (chunk_loc.x * Chunk.CHUNK_SIZE),
									x[1] +  Chunk.CHUNK_SIZE * layer,
									x[2] +  (chunk_loc.z * Chunk.CHUNK_SIZE), side);

							v2 = world_ref.f(x[0] + q[0] +  (chunk_loc.x * Chunk.CHUNK_SIZE),
									x[1] + q[1] +  Chunk.CHUNK_SIZE * layer,
									x[2] + q[2] +  (chunk_loc.z * Chunk.CHUNK_SIZE), side);

							mask[n++] = ((v1 != null && v2 != null
									&& (v1.type == v2.type || (v1.type != BlockType.AIR && v2.type != BlockType.AIR))))
											? null
											: backFace ? v2 : v1;
						}
					// Increment x[d]
					++x[d];
					// Generate mesh for mask using lexicographic ordering
					n = 0;
					for (j = 0; j < dims[v]; ++j)
						for (i = 0; i < dims[u];) {

							if (mask[n] != null && mask[n].type != BlockType.AIR) {
								// Compute width
								for (w = 1; i + w < dims[u] && mask[n + w] != null
										&& (mask[n].type == mask[n + w].type); w++) {
								}
								// Compute height (this is slightly awkward
								boolean done = false;
								for (h = 1; j + h < dims[v]; ++h) {
									for (k = 0; k < w; ++k) {
										if (mask[n + k + h * dims[u]] == null
												|| mask[n + k + h * dims[u]].type != mask[n].type) {
											done = true;
											break;
										}
									}
									if (done) {
										break;
									}
								}
								// Add quad
								x[u] = i;
								x[v] = j;
								int[] du = { 0, 0, 0 }, dv = { 0, 0, 0 };
								// if(mask[n] > 0) {
								du[0] = 0;
								du[1] = 0;
								du[2] = 0;
								du[u] = w;

								dv[0] = 0;
								dv[1] = 0;
								dv[2] = 0;
								dv[v] = h;

								/*
								 * // vertices = ArrayUtils.addAll(vertices, // x[0], x[1], x[2], // x[0] +
								 * du[0], x[1] + du[1], x[2] + du[2], // x[0] + du[0] + dv[0], x[1] + du[1] +
								 * dv[1], x[2] + du[2] + dv[2], // x[0] + dv[0], x[1] + dv[1], x[2] + dv[2]); //
								 * System.out.println(Binary.intToString(vertice1));
								 * 
								 * // pass++; // // switch (mask[n].side) { // default: { // normals =
								 * ArrayUtils.addAll(normals, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0); // break; //
								 * } // case UP: { // normals = ArrayUtils.addAll(normals, 0, 1, 0, 0, 1, 0, 0,
								 * 1, 0, 0, 1, 0); // break; // } // case DOWN: { // normals =
								 * ArrayUtils.addAll(normals, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0); // break;
								 * // } // case EAST: { // normals = ArrayUtils.addAll(normals, 1, 0, 0, 1, 0,
								 * 0, 1, 0, 0, 1, 0, 0); // break; // } // case WEST: { // normals =
								 * ArrayUtils.addAll(normals, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0); // break;
								 * // } // case NORTH: { // normals = ArrayUtils.addAll(normals, 0, 0, 1, 0, 0,
								 * 1, 0, 0, 1, 0, 0, 1); // break; // } // case SOUTH: { // normals =
								 * ArrayUtils.addAll(normals, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1); // break;
								 * // } // // } // texinfo = ArrayUtils.addAll(texinfo, textureID, d == 0 ? h :
								 * w, d == 0 ? w : h, // textureID, d == 0 ? h : w, d == 0 ? w : h, textureID, d
								 * == 0 ? h : w, // d == 0 ? w : h, textureID, d == 0 ? h : w, d == 0 ? w : h);
								 * // if (d == 0) { // uv = ArrayUtils.addAll(uv, 0, 1, 0, 0, 1, 0, 1, 1); // }
								 * else if (d == 2) { // uv = ArrayUtils.addAll(uv, 0, 1, 1, 1, 1, 0, 0, 0); //
								 * } else { // uv = ArrayUtils.addAll(uv, 0, 0, 1, 0, 1, 1, 0, 1); // }
								 *///
								int textureID = Resource.getTextureID(mask[n].type, mask[n].side);

								// attrib1 :
								// x      y    z     w     h   t-id ?
								// 00000 00000 00000 0000 0000 00000000 0 //
								// attrib 2:
								// uv r     g     b   dmg 
								// 00 0000 0000 0000 0000 00000 0000 00000
								int attrib_1_1 = (x[0] << 27) | (x[1] << 22) | (x[2] << 17) | (textureID << 1) | (d == 0 ? (h - 1 << 13) | (w - 1 << 9) : (w - 1 << 13) | (h - 1 << 9));
								int attrib_2_1 = (d==1 ? 0 : 1) << 30 | 15 << 26 | 15 << 22 | 15 << 18;
								
								int attrib_1_2 = ((x[0]+ du[0]) << 27) | ((x[1] + du[1])<< 22) | ((x[2]+ du[2] )<< 17) | (textureID << 1) | (d == 0 ? (h - 1 << 13) | (w - 1 << 9) : (w - 1 << 13) | (h - 1 << 9));
								int attrib_2_2 = (d==1 ? 3 : (d==0) ? 0 : 2 ) << 30 | 15 << 26 | 15 << 22 | 15 << 18;


								int attrib_1_3 = ((x[0]+ du[0]+ dv[0]) << 27) | ((x[1] + du[1]+ dv[1])<< 22) | ((x[2]+ du[2]+ dv[2] )<< 17) | (textureID << 1) | (d == 0 ? (h - 1 << 13) | (w - 1 << 9) : (w - 1 << 13) | (h - 1 << 9));
								int attrib_2_3 = (d==1 ? 2 : 3) << 30 | 15 << 26 | 15 << 22 | 15 << 18;
								
								int attrib_1_4 = ((x[0]+ dv[0] )<< 27) | ((x[1] + dv[1])<< 22) | ((x[2]+  dv[2]) << 17) | (textureID << 1) | (d == 0 ? (h - 1 << 13) | (w - 1 << 9) : (w - 1 << 13) | (h - 1 << 9));
								int attrib_2_4 = (d==1 ? 1 : (d==0) ? 2 : 0) << 30 | 15 << 26 | 15 << 22 | 15 << 18;
								
								
							
								
								attrib1 = ArrayUtils.addAll(attrib1,  new int[] {attrib_1_1,attrib_1_2,attrib_1_3,attrib_1_4});
								attrib2 = ArrayUtils.addAll(attrib2, new int[] {attrib_2_1,attrib_2_2,attrib_2_3,attrib_2_4});

								if (mask[n].side == UP || mask[n].side == EAST || mask[n].side == NORTH) {
									indices = ArrayUtils.addAll(indices, 4 * pass + 2, 4 * pass + 1, 4 * pass + 0,
											4 * pass + 0, 4 * pass + 3, 4 * pass + 2);
								} else {
									indices = ArrayUtils.addAll(indices, 4 * pass + 0, 4 * pass + 1, 4 * pass + 2,
											4 * pass + 2, 4 * pass + 3, 4 * pass + 0);
								}
								pass++;



								// Zero-out mask
								for (l = 0; l < h; ++l)
									for (k = 0; k < w; ++k) {
										mask[n + k + l * dims[u]] = null;
									}
								// Increment counters and continue
								i += w;
								n += w;
							} else {
								++i;
								++n;
							}
						}
				}
			}
		}
		TerrainMesh mesh = new TerrainMesh();
		mesh.indices=indices;
		mesh.attrib1 =attrib1;
		mesh.attrib2 =attrib2;
		mesh.chunk_loc = chunk_loc;

		jobs.remove();
		finished.add(mesh);
	}

	@Override
	public void run() {
		while (hasJob()) {
			greedy();
		}

	}
}
