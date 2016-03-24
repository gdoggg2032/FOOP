package mrfoops;
/** this class record some appearence settings */
public class BoardInfo{
	/** w = width of the board (only block area), pixel-unit */
	public int w;
	/** h = height of the board (only block area), pixel-unit */
	public int h;
	/** width of a block, pixel-unit */
	public int dw;
	/** height of a block, pixel-unit */
	public int dh;
	/** the position of bias (the starting coordinate of blocks)*/
	public Coordinate oriC;
	/** the y-axis number of blocks */
	public int row;
	/** the x-axis number of blocks */
	public int col;
	/** the bias of an object in a block (to let the object draw in the middle of a block) */
	public Coordinate objectShiftC;

	public BoardInfo(){
		this.w = 400;
		this.h = 320;
		this.row = 8;
		this.col = 8;
		this.dw = (this.w/this.col);
		this.dh = (this.h/this.col);
		this.oriC = new Coordinate(50, 40);
		this.objectShiftC = new Coordinate(25, 15);

	}
}