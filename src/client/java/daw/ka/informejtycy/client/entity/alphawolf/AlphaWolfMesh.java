package daw.ka.informejtycy.client.entity.alphawolf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Direction;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Builds a vanilla ModelPart tree from a generated .bin mesh.
 * Vanilla's CubeListBuilder only supports box UV, but these models give every face its own
 * texture rectangle, so the polygons of each cube are replaced with per-face UVs.
 */
public final class AlphaWolfMesh {
	private static final int MAGIC = 0x41574D31;
	private static final String PATH = "/assets/informejtycy/models/entity/alpha_wolf/";

	// file order: north, south, east, west, up, down (Blockbench names; east is -X in model space)
	private static final Vector3f[] NORMALS = {
		new Vector3f(0, 0, -1), new Vector3f(0, 0, 1), new Vector3f(-1, 0, 0),
		new Vector3f(1, 0, 0), new Vector3f(0, -1, 0), new Vector3f(0, 1, 0)
	};
	private static final Direction[] DIRECTIONS = {
		Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP
	};

	private record Bone(String parent, float x, float y, float z, List<ModelPart.Cube> cubes) {}

	private AlphaWolfMesh() {}

	/** @param name file name without extension, for example "standing" */
	public static ModelPart load(String name) {
		try (InputStream raw = AlphaWolfMesh.class.getResourceAsStream(PATH + name + ".bin")) {
			if (raw == null) throw new IllegalStateException("Missing alpha wolf mesh: " + name);
			return read(new DataInputStream(new BufferedInputStream(raw)));
		} catch (IOException e) {
			throw new UncheckedIOException("Could not read alpha wolf mesh: " + name, e);
		}
	}

	/** Loads a mesh moved sideways, to centre a lopsided pose on the hitbox. Offsets are in model units. */
	public static ModelPart load(String name, float offsetX, float offsetZ) {
		ModelPart top = load(name);
		ModelPart root = top.getChild("wolf_root");
		root.x += offsetX;
		root.z += offsetZ;
		root.setInitialPose(root.storePose());
		return top;
	}

	private static ModelPart read(DataInputStream in) throws IOException {
		if (in.readInt() != MAGIC) throw new IOException("Not an alpha wolf mesh");
		float texWidth = in.readUnsignedShort();
		float texHeight = in.readUnsignedShort();
		int boneCount = in.readUnsignedShort();

		Map<String, Bone> bones = new LinkedHashMap<>();
		Map<String, List<String>> children = new HashMap<>();
		for (int i = 0; i < boneCount; i++) {
			String name = in.readUTF();
			String parent = in.readUTF();
			float x = in.readFloat(), y = in.readFloat(), z = in.readFloat();
			int cubeCount = in.readInt();
			List<ModelPart.Cube> cubes = new ArrayList<>(cubeCount);
			for (int c = 0; c < cubeCount; c++) cubes.add(readCube(in, texWidth, texHeight));
			bones.put(name, new Bone(parent, x, y, z, cubes));
			children.computeIfAbsent(parent, key -> new ArrayList<>()).add(name);
		}

		// an empty top part, so that "root" is an ordinary named bone for the animations
		Map<String, ModelPart> top = new HashMap<>();
		for (String name : children.getOrDefault("", List.of())) top.put(name, build(name, bones, children));
		return new ModelPart(List.of(), top);
	}

	private static ModelPart build(String name, Map<String, Bone> bones, Map<String, List<String>> children) {
		Map<String, ModelPart> parts = new HashMap<>();
		for (String child : children.getOrDefault(name, List.of())) parts.put(child, build(child, bones, children));
		Bone bone = bones.get(name);
		ModelPart part = new ModelPart(bone.cubes(), parts);
		part.setPos(bone.x(), bone.y(), bone.z());
		part.setInitialPose(part.storePose());
		return part;
	}

	private static ModelPart.Cube readCube(DataInputStream in, float texWidth, float texHeight) throws IOException {
		float x0 = in.readFloat(), y0 = in.readFloat(), z0 = in.readFloat();
		float x1 = in.readFloat(), y1 = in.readFloat(), z1 = in.readFloat();
		int faceCount = in.readUnsignedByte();

		int[] dir = new int[faceCount];
		int[][] uv = new int[faceCount][4];
		EnumSet<Direction> visible = EnumSet.noneOf(Direction.class);
		for (int f = 0; f < faceCount; f++) {
			dir[f] = in.readByte();
			for (int k = 0; k < 4; k++) uv[f][k] = in.readUnsignedShort();
			visible.add(DIRECTIONS[dir[f]]);
		}

		// the constructor sizes the polygon array; its box UV polygons are then overwritten
		ModelPart.Cube cube = new FaceUvCube(x0, y0, z0, x1, y1, z1, visible);
		for (int f = 0; f < faceCount; f++) {
			cube.polygons[f] = polygon(dir[f], uv[f], x0, y0, z0, x1, y1, z1, texWidth, texHeight);
		}
		return cube;
	}

	private static ModelPart.Polygon polygon(int dir, int[] uv, float x0, float y0, float z0, float x1, float y1, float z1,
											 float texWidth, float texHeight) {
		// corners in texture order: (u0,v0) (u1,v0) (u1,v1) (u0,v1)
		float[][] p = switch (dir) {
			case 0 -> new float[][]{{x0, y0, z0}, {x1, y0, z0}, {x1, y1, z0}, {x0, y1, z0}};
			case 1 -> new float[][]{{x1, y0, z1}, {x0, y0, z1}, {x0, y1, z1}, {x1, y1, z1}};
			case 2 -> new float[][]{{x0, y0, z1}, {x0, y0, z0}, {x0, y1, z0}, {x0, y1, z1}};
			case 3 -> new float[][]{{x1, y0, z0}, {x1, y0, z1}, {x1, y1, z1}, {x1, y1, z0}};
			case 4 -> new float[][]{{x1, y0, z0}, {x0, y0, z0}, {x0, y0, z1}, {x1, y0, z1}};
			default -> new float[][]{{x1, y1, z1}, {x0, y1, z1}, {x0, y1, z0}, {x1, y1, z0}};
		};
		float u0 = uv[0] / texWidth, v0 = uv[1] / texHeight, u1 = uv[2] / texWidth, v1 = uv[3] / texHeight;
		float[][] t = {{u0, v0}, {u1, v0}, {u1, v1}, {u0, v1}};

		ModelPart.Vertex[] vertices = new ModelPart.Vertex[4];
		for (int i = 0; i < 4; i++) {
			int c = 3 - i; // vanilla winding: reversed relative to the texture order above
			vertices[i] = new ModelPart.Vertex(p[c][0], p[c][1], p[c][2], t[c][0], t[c][1]);
		}
		return new ModelPart.Polygon(vertices, NORMALS[dir]);
	}

	/**
	 * Sodium replaces Cube.compile with its own copy of the cube, built from the box UV
	 * constructor arguments, so the replaced polygons would be ignored. Overriding compile keeps them.
	 */
	private static final class FaceUvCube extends ModelPart.Cube {
		private FaceUvCube(float x0, float y0, float z0, float x1, float y1, float z1, Set<Direction> visible) {
			super(0, 0, x0, y0, z0, x1 - x0, y1 - y0, z1 - z0, 0, 0, 0, false, 1, 1, visible);
		}

		@Override
		public void compile(PoseStack.Pose pose, @NonNull VertexConsumer buffer, int light, int overlay, int color) {
			Matrix4f matrix = pose.pose();
			Vector3f scratch = new Vector3f();
			for (ModelPart.Polygon polygon : polygons) {
				Vector3f normal = pose.transformNormal(polygon.normal(), scratch);
				float nx = normal.x(), ny = normal.y(), nz = normal.z();
				for (ModelPart.Vertex vertex : polygon.vertices()) {
					Vector3f pos = matrix.transformPosition(vertex.worldX(), vertex.worldY(), vertex.worldZ(), scratch);
					buffer.addVertex(pos.x(), pos.y(), pos.z(), color, vertex.u(), vertex.v(), overlay, light, nx, ny, nz);
				}
			}
		}
	}
}
