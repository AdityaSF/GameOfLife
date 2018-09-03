package adiitya.life;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public final class World {

	private byte[][] grid;
	private float speed;
	private int width;
	private int height;
	private long generation;
	private float scale = 16F;

	private float elapsed = 0F;

	public World(int width, int height, float speed) {
		grid = createEmptyWorld(width, height);
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.generation = 0;
	}

	private byte[][] createEmptyWorld(int width, int height) {

		byte[][] grid = new byte[width][height];
		Random r = new Random();

		for (int i = 0; i < width * height; i++)
			grid[i / width][i % width] = (byte) r.nextInt(2);

		return grid;
	}

	/**
	 * This method renders the game world.
	 *
	 * @param renderer The renderer. Always started and set to filled mode
	 */
	public void render(ShapeRenderer renderer, OrthographicCamera cam) {

		Vector3 tl = cam.unproject(new Vector3(0, 0, 0));
		Vector3 br = cam.unproject(new Vector3(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0));

		int minX = (int) ((int) (16 * Math.ceil(tl.x / 16)) / 16 - 16);
		int maxX = (int) ((int) (16 * Math.ceil(br.x / 16)) / 16 + 16);
		int minY = (int) ((int) (16 * Math.ceil(tl.y / 16)) / 16 - 16);
		int maxY = (int) ((int) (16 * Math.ceil(br.y / 16)) / 16 + 16);

		renderer.setColor(Color.WHITE);

		for (int y = minY; y < maxY; y++) {
			for (int x = minX; x < maxX; x++) {
				if (get(x, y) == 1)
					renderer.box(x * 16, y * 16, scale, 16, 16, 0);
			}
		}
	}

	public void update() {

		elapsed += Gdx.graphics.getDeltaTime();

		while (elapsed >= speed) {
			elapsed -= speed;

			byte[][] newGrid = new byte[width][height];

			for (int i = 0; i < width * height; i++) {

				int x = i % width;
				int y = i / height;

				// cells are stored as 0x00 and 0x01 so only live cells will increase neighbour count
				byte neighbours = getNeighbours(x, y);
				byte cell = grid[x][y];

				if (cell == 1)
					newGrid[x][y] = (byte) (neighbours > 1 && neighbours < 4 ? 1 : 0);
				else
					newGrid[x][y] = (byte) (neighbours == 3 ? 1 : 0);
			}

			grid = newGrid;
			generation++;
		}
	}

	public long getGeneration() {
		return generation;
	}

	private byte get(int x, int y) {

		x %= width;
		y %= height;

		x = x < 0 ? Math.abs(width + x) : x;
		y = y < 0 ? Math.abs(height + y) : y;

		return grid[x][y];
	}

	private byte getNeighbours(int x, int y) {

		byte neighbours = 0;

		neighbours += get(x, y-1); //top
		neighbours += get(x+1, y-1); //tr
		neighbours += get(x+1, y); //r
		neighbours += get(x+1, y+1); //br
		neighbours += get(x, y+1); //b
		neighbours += get(x-1, y+1); //bl
		neighbours += get(x-1, y); //l
		neighbours += get(x-1, y-1); //tl

		return neighbours;
	}

	public void zoomOut() {
		scale -= 2.5 * Gdx.graphics.getDeltaTime();
	}

	public void zoomIn() {
		scale += 2.5 * Gdx.graphics.getDeltaTime();
	}

	public void reset() {
		grid = createEmptyWorld(width, height);
	}
}
