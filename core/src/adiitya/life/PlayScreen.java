package adiitya.life;

import adiitya.life.input.GameInput;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class PlayScreen implements Screen {

	private final Main game;
	private float elapsed;

	private ShapeRenderer renderer;
	private World world;

	private OrthographicCamera textCam;
	private SpriteBatch fontBatch;
	private BitmapFont font;

	private GameInput input;

	public PlayScreen(Main game) {
		this.game = game;
	}

	@Override
	public void show() {

		renderer = new ShapeRenderer();
		world = new World(256, 256, 0.1F);

		textCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		textCam.setToOrtho(true);
		fontBatch = new SpriteBatch();
		font = getFont();

		elapsed = 0F;

		input = new GameInput(game, world);
		Gdx.input.setInputProcessor(input);
	}

	private BitmapFont getFont() {

		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = 24;
		param.flip = true;
		param.genMipMaps = false;

		BitmapFont font = gen.generateFont(param);
		gen.dispose();

		return font;
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

		Vector3 tl = textCam.unproject(new Vector3(0, 0, 0));

		fontBatch.begin();
		font.draw(fontBatch, String.format("FPS: %d", Gdx.graphics.getFramesPerSecond()), tl.x + 10, tl.y + 10);
		font.draw(fontBatch, String.format("Pos: (%d, %d)", (int) game.getCamera().position.x / 16, (int) -(game.getCamera().position.y / 16)), tl.x + 10, tl.y + 36);
		fontBatch.end();
	}

	@Override
	public void resize(int width, int height) {

		textCam.viewportWidth = width;
		textCam.viewportHeight = height;
		textCam.update();

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
		fontBatch.dispose();
		font.dispose();
	}

	private void updateRenderer() {
		renderer.setProjectionMatrix(game.getCamera().combined);
		fontBatch.setProjectionMatrix(textCam.combined);
	}
}
