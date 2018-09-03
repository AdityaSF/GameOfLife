package adiitya.life.input;

import adiitya.life.Main;
import adiitya.life.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import java.util.HashMap;

public class GameInput implements InputProcessor {

	private final Main game;
	private final World world;
	
	private final HashMap<Integer, KeyStroke> keys = new HashMap<>();

	private KeyStroke MOVE_UP = new KeyStroke(51);
	private KeyStroke MOVE_LEFT = new KeyStroke(29);
	private KeyStroke MOVE_DOWN = new KeyStroke(47);
	private KeyStroke MOVE_RIGHT = new KeyStroke(32);

	private KeyStroke ZOOM_IN = new KeyStroke(69);
	private KeyStroke ZOOM_OUT = new KeyStroke(70);

	private KeyStroke RESET = new KeyStroke(131);

	private static final float CAMERA_SPEED = 10F * 16;

	public GameInput(Main game, World world) {
		
		this.game = game;
		this.world = world;
		
		init();
	}
	
	private void init() {

		MOVE_UP = create(51);
		MOVE_LEFT = create(29);
		MOVE_DOWN = create(47);
		MOVE_RIGHT = create(32);

		ZOOM_IN = create(69);
		ZOOM_OUT = create(70);
	}
	
	private KeyStroke create(int id) {
		
		KeyStroke key = new KeyStroke(id);
		keys.put(id, key);
		
		return key;
	}

	public void poll() {

		float x = 0F;
		float y = 0F;

		if (MOVE_UP.isPressed) y -= (CAMERA_SPEED * game.getCamera().zoom) * Gdx.graphics.getDeltaTime();
		if (MOVE_DOWN.isPressed) y += (CAMERA_SPEED * game.getCamera().zoom) * Gdx.graphics.getDeltaTime();
		if (MOVE_LEFT.isPressed) x -= (CAMERA_SPEED * game.getCamera().zoom) * Gdx.graphics.getDeltaTime();
		if (MOVE_RIGHT.isPressed) x += (CAMERA_SPEED * game.getCamera().zoom) * Gdx.graphics.getDeltaTime();

		if (ZOOM_IN.isPressed) game.getCamera().zoom += 0.025F * (game.getCamera().zoom / 2);
		if (ZOOM_OUT.isPressed) game.getCamera().zoom -= 0.025F * (game.getCamera().zoom / 2);

		if (RESET.isJustPressed()) world.reset();

		game.getCamera().translate(x, y);
		game.getCamera().update();
	}

	@Override
	public boolean keyDown(int keycode) {

		keys.getOrDefault(keycode, new KeyStroke(keycode))
				.press();

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		keys.getOrDefault(keycode, new KeyStroke(keycode))
				.release();

		return false;
	}

	// ===== UNUSED =====

	@Override public boolean keyTyped(char character) { return false; }
	@Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
	@Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
	@Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
	@Override public boolean mouseMoved(int screenX, int screenY) { return false; }
	@Override public boolean scrolled(int amount) { return false; }
}
