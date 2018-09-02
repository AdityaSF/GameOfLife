package adiitya.life;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PlayScreen implements Screen {

	private final Main game;
	private float elapsed;

	private ShapeRenderer renderer;
	private World world;

	public PlayScreen(Main game) {
		this.game = game;
	}

	@Override
	public void show() {

		renderer = new ShapeRenderer();
		world = new World(256, 256, 0.01F);
		elapsed = 0F;
	}

	@Override
	public void render(float delta) {

		elapsed += Gdx.graphics.getDeltaTime();

		updateRenderer();
		world.update();
		renderer.begin(ShapeType.Filled);
		world.render(renderer, game.getCamera());
		renderer.end();

		if (elapsed >= 1F) {
			elapsed -= 1F;
			Gdx.app.log("FPS", String.format("%s, POS: (%d, %d), GEN: %d", Gdx.graphics.getFramesPerSecond(), (int) game.getCamera().position.x / 16, (int) game.getCamera().position.y / 16, world.getGeneration()));
		}
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
