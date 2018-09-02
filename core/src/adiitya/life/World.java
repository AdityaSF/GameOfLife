package adiitya.life;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public final class World {

	private byte[][] grid;
	private float speed;
	private int width;
	private int height;

	private float elapsed = 0F;

	public World(int width, int height, boolean randomize, float speed) {
		grid = createEmptyWorld(width, height, randomize);
		this.speed = speed;
		this.width = width;
		this.height = height;
	}

	private byte[][] createEmptyWorld(int width, int height, boolean randomize) {

		byte[][] grid = new byte[width][height];
		Random r = new Random();

		for (int i = 0; i < width * height; i++)
			grid[i / width][i % width] = randomize ? (byte) r.nextInt(2) : 0;

		if (!randomize) {
			grid[1][0] = 1;
			grid[2][0] = 1;
			grid[0][1] = 1;
			grid[1][1] = 1;
			grid[1][2] = 1;
		}

		return grid;
	}

	/**
	 * This method renders the game world.
	 *
	 * @param renderer The renderer. Always started and set to filled mode
	 */
	public void render(ShapeRenderer renderer) {

		renderer.setColor(Color.WHITE);

		for (int i = 0; i < width * height; i++) {

			int x = i / width, y = i % height;

			if (grid[x][y] == 1)
			//renderer.setColor(grid[x][y] == 0 ? Color.RED : Color.WHITE);
				renderer.box(x * 16, y * 16, 0, 16, 16, 0);
		}
	}

	public void update() {

		elapsed += Gdx.graphics.getDeltaTime();

		if (elapsed >= speed) {
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

				//System.out.printf("%x > %x\n", cell, newGrid[x][y]);
			}

			grid = newGrid;
		}
	}

	private byte getNeighbours(int x, int y) {

		byte neighbours = 0;

		//System.out.printf("[%d, %d]\n", x, y);
		neighbours += getNeighbour(x, y, 0, -1); //top
		neighbours += getNeighbour(x, y, 1, -1); //tr
		neighbours += getNeighbour(x, y, 1, 0); //r
		neighbours += getNeighbour(x, y, 1, 1); //br
		neighbours += getNeighbour(x, y, 0, 1); //b
		neighbours += getNeighbour(x, y, -1, 1); //bl
		neighbours += getNeighbour(x, y, -1, 0); //l
		neighbours += getNeighbour(x, y, -1, -1); //tl

		return neighbours;
	}

	private byte getNeighbour(int x, int y, int xDir, int yDir) {

		int cellX = (x + xDir) % width;
		int cellY = (y + yDir) % height;

		cellX = cellX < 0 ? Math.abs(width + cellX) : cellX;
		cellY = cellY < 0 ? Math.abs(height + cellY) : cellY;

		//System.out.printf("(%d, %d) = %x\n", cellX, cellY, grid[cellX][cellY]);

		return grid[cellX][cellY];
	}
}
