package adiitya.life;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PlayScreen implements Screen {

	private final Main game;

	private ShapeRenderer renderer;
	private World world;

	public PlayScreen(Main game) {
		this.game = game;
	}

	@Override
	public void show() {

		renderer = new ShapeRenderer();
		world = new World(256, 256, true, 0.1F);
	}

	@Override
	public void render(float delta) {

		updateRenderer();
		world.update();
		renderer.begin(ShapeType.Filled);
		world.render(renderer);
		renderer.end();
	}

	@Override
	public void resize(int width, int height) {
		updateRenderer();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		renderer.dispose();
	}

	private void updateRenderer() {
		renderer.setProjectionMatrix(game.getCamera().combined);
	}
}
