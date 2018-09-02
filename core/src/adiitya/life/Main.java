package adiitya.life;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {

	private OrthographicCamera cam;

	@Override
	public void create () {
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.setToOrtho(true);
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			this.screen.dispose();
			setScreen(new PlayScreen(this));
		}

		if (Gdx.input.isKeyPressed(Input.Keys.MINUS))
			cam.zoom += 0.025;

		if (Gdx.input.isKeyPressed(Input.Keys.EQUALS))
			cam.zoom -= 0.025;

		float x = 0F;
		float y = 0F;
		final float speed = 4F;

		if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= speed * cam.zoom;
		if (Gdx.input.isKeyPressed(Input.Keys.D)) x += speed * cam.zoom;
		if (Gdx.input.isKeyPressed(Input.Keys.W)) y -= speed * cam.zoom;
		if (Gdx.input.isKeyPressed(Input.Keys.S)) y += speed * cam.zoom;

		cam.translate(x, y);
		cam.update();
		this.screen.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {

		cam.viewportWidth = width;
		cam.viewportHeight = height;
		cam.update();

		this.screen.resize(width, height);
	}

	@Override
	public void dispose () {
		this.screen.dispose();
	}

	public OrthographicCamera getCamera() {
		return cam;
	}
}
