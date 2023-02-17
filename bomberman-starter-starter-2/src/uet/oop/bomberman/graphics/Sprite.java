package uet.oop.bomberman.graphics;

import javafx.scene.image.*;

/**
 * Lưu trữ thông tin các pixel của 1 sprite (hình ảnh game)
 */
public class Sprite {
	public static final int DEFAULT_SIZE = 16;
	public static final int SCALED_SIZE = DEFAULT_SIZE * 2;
	private static final int TRANSPARENT_COLOR = 0xffff00ff;
	public final int SIZE;
	private int _x, _y;
	public int[] _pixels;
	protected int _realWidth;
	protected int _realHeight;
	private SpriteSheet _sheet;

	/**
	 * Định vị trên ảnh
	 */
	public static final int minvoRotate = 0;	//1
	public static final int redMinvoRotate = 7;	//2

	public static final int kondoria = 14;		//3
	public static final int minvo = 21;			//4
	public static final int ghost = 28;			//5
	public static final int pass = 35;			//6
	public static final int doll = 45;			//7
	public static final int oneal = 52;			//8
	public static final int balloom = 59;		//9

	/**
	 * Định vị trên ảnh item
	 */
	public static final int powerup_wallpass = 0;	//đi xuyên tường (ok)
	public static final int powerup_bombpass = 1;	//đi xuyên qua bom (ok)
	public static final int powerup_detonator = 2;	//nổ xuyên tường
	public static final int powerup_speed = 3;		//tăng tốc (ok)
	public static final int powerup_flamepass = 4;	//miễn nổ (ok)
	public static final int random_item = 5;		//item ngẫu nhiên (ok)
	public static final int powerup_bombs = 6;		//tăng lượng bom (ok)
	public static final int powerup_flames = 7;		//tăng phạm vi (ok)

	public static final int item9 = 8;
	public static final int item10 = 9;
	public static final int item11 = 10;
	public static final int item12 = 11;
	public static final int item13 = 12;
	public static final int item14 = 13;
	public static final int portal = 15;

	public static Sprite grass = new Sprite(DEFAULT_SIZE, 6, 0, SpriteSheet.alltiles, 16, 16);
	public static Sprite brick = new Sprite(DEFAULT_SIZE, 7, 0, SpriteSheet.alltiles, 16, 16);
	public static Sprite wall = new Sprite(DEFAULT_SIZE, 5, 0, SpriteSheet.alltiles, 16, 16);
	public static Sprite glass = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.tiles, 16, 16);
	//public static Sprite portal = new Sprite(DEFAULT_SIZE, 4, 0, SpriteSheet.alltiles, 14, 14);

	/*
	|--------------------------------------------------------------------------
	| Bomber Sprites
	|--------------------------------------------------------------------------
	 */

	/*
	|--------------------------------------------------------------------------
	| Bomb Sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite bomb = new Sprite(DEFAULT_SIZE, 0, 3, SpriteSheet.alltiles, 15, 15);
	public static Sprite bomb_1 = new Sprite(DEFAULT_SIZE, 1, 3, SpriteSheet.alltiles, 13, 15);
	public static Sprite bomb_2 = new Sprite(DEFAULT_SIZE, 2, 3, SpriteSheet.alltiles, 12, 14);

	/*
	|--------------------------------------------------------------------------
	| FlameSegment Sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite bomb_exploded = new Sprite(DEFAULT_SIZE, 0, 4, SpriteSheet.alltiles, 16, 16);
	public static Sprite bomb_exploded1 = new Sprite(DEFAULT_SIZE, 0, 5, SpriteSheet.alltiles, 16, 16);
	public static Sprite bomb_exploded2 = new Sprite(DEFAULT_SIZE, 0, 6, SpriteSheet.alltiles, 16, 16);

	public static Sprite explosion_vertical = new Sprite(DEFAULT_SIZE, 1, 5, SpriteSheet.alltiles, 16, 16);
	public static Sprite explosion_vertical1 = new Sprite(DEFAULT_SIZE, 2, 5, SpriteSheet.alltiles, 16, 16);
	public static Sprite explosion_vertical2 = new Sprite(DEFAULT_SIZE, 3, 5, SpriteSheet.alltiles, 16, 16);

	public static Sprite explosion_horizontal = new Sprite(DEFAULT_SIZE, 1, 7, SpriteSheet.alltiles, 16, 16);
	public static Sprite explosion_horizontal1 = new Sprite(DEFAULT_SIZE, 1, 8, SpriteSheet.alltiles, 16, 16);
	public static Sprite explosion_horizontal2 = new Sprite(DEFAULT_SIZE, 1, 9, SpriteSheet.alltiles, 16, 16);

	public static Sprite explosion_horizontal_left_last = new Sprite(DEFAULT_SIZE, 0, 7, SpriteSheet.alltiles, 16, 16);
	public static Sprite explosion_horizontal_left_last1 = new Sprite(DEFAULT_SIZE, 0, 8, SpriteSheet.alltiles, 16, 16);
	public static Sprite explosion_horizontal_left_last2 = new Sprite(DEFAULT_SIZE, 0, 9, SpriteSheet.alltiles, 16, 16);

	public static Sprite explosion_horizontal_right_last = new Sprite(DEFAULT_SIZE, 2, 7, SpriteSheet.alltiles, 16, 16);
	public static Sprite explosion_horizontal_right_last1 = new Sprite(DEFAULT_SIZE, 2, 8, SpriteSheet.alltiles, 16, 16);
	public static Sprite explosion_horizontal_right_last2 = new Sprite(DEFAULT_SIZE, 2, 9, SpriteSheet.alltiles, 16, 16);

	public static Sprite explosion_vertical_top_last = new Sprite(DEFAULT_SIZE, 1, 4, SpriteSheet.alltiles, 16, 16);
	public static Sprite explosion_vertical_top_last1 = new Sprite(DEFAULT_SIZE, 2, 4, SpriteSheet.alltiles, 16, 16);
	public static Sprite explosion_vertical_top_last2 = new Sprite(DEFAULT_SIZE, 3, 4, SpriteSheet.alltiles, 16, 16);

	public static Sprite explosion_vertical_down_last = new Sprite(DEFAULT_SIZE, 1, 6, SpriteSheet.alltiles, 16, 16);
	public static Sprite explosion_vertical_down_last1 = new Sprite(DEFAULT_SIZE, 2, 6, SpriteSheet.alltiles, 16, 16);
	public static Sprite explosion_vertical_down_last2 = new Sprite(DEFAULT_SIZE, 3, 6, SpriteSheet.alltiles, 16, 16);

	/*
	|--------------------------------------------------------------------------
	| Brick FlameSegment
	|--------------------------------------------------------------------------
	 */
	public static Sprite brick_exploded = new Sprite(DEFAULT_SIZE, 7, 1, SpriteSheet.alltiles, 16, 16);
	public static Sprite brick_exploded1 = new Sprite(DEFAULT_SIZE, 7, 2, SpriteSheet.alltiles, 16, 16);
	public static Sprite brick_exploded2 = new Sprite(DEFAULT_SIZE, 7, 3, SpriteSheet.alltiles, 16, 16);

	/*
	|--------------------------------------------------------------------------
	| Powerups
	|--------------------------------------------------------------------------
	 */
	/*public static Sprite powerup_bombs = new Sprite(DEFAULT_SIZE, 0, 10, SpriteSheet.alltiles, 16, 16);
	public static Sprite powerup_flames = new Sprite(DEFAULT_SIZE, 1, 10, SpriteSheet.alltiles, 16, 16);
	public static Sprite powerup_speed = new Sprite(DEFAULT_SIZE, 2, 10, SpriteSheet.alltiles, 16, 16);
	public static Sprite powerup_wallpass = new Sprite(DEFAULT_SIZE, 3, 10, SpriteSheet.alltiles, 16, 16);
	public static Sprite powerup_detonator = new Sprite(DEFAULT_SIZE, 4, 10, SpriteSheet.alltiles, 16, 16);
	public static Sprite powerup_bombpass = new Sprite(DEFAULT_SIZE, 5, 10, SpriteSheet.alltiles, 16, 16);
	public static Sprite powerup_flamepass = new Sprite(DEFAULT_SIZE, 6, 10, SpriteSheet.alltiles, 16, 16);
*/


	public Sprite(int size, int x, int y, SpriteSheet sheet, int rw, int rh) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		_x = x * SIZE;
		_y = y * SIZE;
		_sheet = sheet;
		_realWidth = rw;
		_realHeight = rh;
		load();
	}

	public Sprite(int size, int color) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		setColor(color);
	}

	private void setColor(int color) {
		for (int i = 0; i < _pixels.length; i++) {
			_pixels[i] = color;
		}
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				_pixels[x + y * SIZE] = _sheet._pixels[(x + _x) + (y + _y) * _sheet.SIZE];
			}
		}
	}

	public static Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2, int animate, int time) {
		int calc = animate % time;
		int diff = time / 3;

		if(calc < diff) {
			return normal;
		}

		if(calc < diff * 2) {
			return x1;
		}

		return x2;
	}

	public static Sprite movingSprite(Sprite x1, Sprite x2, int animate, int time) {
		int diff = time / 2;
		return (animate % time > diff) ? x1 : x2;
	}

	public int getSize() {
		return SIZE;
	}

	public int getPixel(int i) {
		return _pixels[i];
	}

	public Image getFxImage() {
		WritableImage wr = new WritableImage(SIZE, SIZE);
		PixelWriter pw = wr.getPixelWriter();
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				if ( _pixels[x + y * SIZE] == TRANSPARENT_COLOR) {
					pw.setArgb(x, y, 0);
				}
				else {
					pw.setArgb(x, y, _pixels[x + y * SIZE]);
				}
			}
		}
		Image input = new ImageView(wr).getImage();
		return resample(input, SCALED_SIZE / DEFAULT_SIZE);
	}

	private Image resample(Image input, int scaleFactor) {
		final int W = (int) input.getWidth();
		final int H = (int) input.getHeight();
		final int S = scaleFactor;

		WritableImage output = new WritableImage(
				W * S,
				H * S
		);

		PixelReader reader = input.getPixelReader();
		PixelWriter writer = output.getPixelWriter();

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				final int argb = reader.getArgb(x, y);
				for (int dy = 0; dy < S; dy++) {
					for (int dx = 0; dx < S; dx++) {
						writer.setArgb(x * S + dx, y * S + dy, argb);
					}
				}
			}
		}

		return output;
	}
}
