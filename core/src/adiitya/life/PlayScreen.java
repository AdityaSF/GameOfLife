package adiitya.life;

import adiitya.life.input.GameInput;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PlayScreen implements Screen {

	private final Main game;
	private float elapsed;

	private ShapeRenderer renderer;
	private World world;

	private GameInput input;

	public PlayScreen(Main game) {
		this.game = game;
	}

	@Override
	public void show() {

		renderer = new ShapeRenderer();
		world = new World(256, 256, 0.1F);
		elapsed = 0F;

		input = new GameInput(game, world);
		Gdx.input.setInputProcessor(input);
	}

	@Override
	public void render(float delta) {

		elapsed += Gdx.graphics.getDeltaTime();

		input.poll();
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
