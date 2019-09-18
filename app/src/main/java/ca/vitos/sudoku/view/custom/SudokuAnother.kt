package ca.vitos.sudoku.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class SudokuAnother @JvmOverloads constructor(

    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    val sqrtSize = 3
    val size = 9
    var celSizeInPx = 0F

    var selectedRow = 0
    var selectedCol = 0

    private var listener: SudokuAnother.OnToucListener? = null

    val mainBoxPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 8f
        style = Paint.Style.STROKE
    }
    val gridMainLinePaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 8f
        style = Paint.Style.STROKE
    }
    val gridMainPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 4f
        style = Paint.Style.STROKE
    }

    val selectedCellPaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.FILL_AND_STROKE
    }
    val conflictingCellPaint = Paint().apply {
        color = Color.parseColor("#D3D3D3")
        style = Paint.Style.FILL_AND_STROKE
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePx = min(widthMeasureSpec,heightMeasureSpec)
        setMeasuredDimension(sizePx,sizePx)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        celSizeInPx = (width / size).toFloat()
        canvas.drawRect(0f,0f,width.toFloat(),height.toFloat(),mainBoxPaint)
        fillCells(canvas)
        drawLines(canvas)
    }
    private fun fillCells(canvas: Canvas){
        if ( selectedRow == -1 && selectedCol == -1 ) return

        for (row in 0..size){
            for ( col in 0..size ){
                if (col == selectedCol && row == selectedRow){
                    fillCell(canvas,row,col,selectedCellPaint)
                }else if ( col == selectedCol || row == selectedRow ) {
                    fillCell(canvas,row,col,conflictingCellPaint)
                } else if ( col / sqrtSize == selectedCol / sqrtSize && row /sqrtSize == selectedRow / sqrtSize ) {
                    fillCell(canvas,row,col,conflictingCellPaint)
                }
            }
        }

    }
    private fun fillCell(canvas: Canvas, row : Int, col : Int ,paint: Paint){
        canvas.drawRect(
            col * celSizeInPx,
            row * celSizeInPx,
            (col + 1) * celSizeInPx,
            (row + 1) * celSizeInPx,
            paint
        )
    }
    private fun drawLines(canvas: Canvas) {
        var paint: Paint
        for(i in 0 until size){
            paint = when (i % sqrtSize) {
                0 -> gridMainLinePaint
                else -> gridMainPaint
            }
            //Vertical Lines
            canvas.drawLine(
                (celSizeInPx * i),
                0f,
                (celSizeInPx * i),
                height.toFloat(),
                paint
            )
            //Draw Horizontal Lines
            canvas.drawLine(
                0f,
                (celSizeInPx * i) ,
                width.toFloat(),
                (celSizeInPx * i),
                paint
            )
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        Log.d("TesTing","This is anopther test for Key Down")
        return when (event.action) {
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                if (selectedCol < size)
                    selectedCol++
                true
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                if (selectedCol >= -1)
                    selectedCol--
                true
            }
            else -> false

        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val posibleSelectedRow = (event.y / celSizeInPx).toInt()
                val posibleSelectedCol = (event.x / celSizeInPx).toInt()
                listener?.onCellTouched(posibleSelectedRow,posibleSelectedCol)
                true
            }
            else -> false
        }
    }
    fun updateSelectedCellUI(row: Int,col: Int) {
        selectedRow = row
        selectedCol = col
        invalidate()
    }
    fun registerListener (listener: SudokuAnother.OnToucListener){
        this.listener = listener
    }
    interface OnToucListener {
        fun onCellTouched(row: Int, col: Int)
    }
}