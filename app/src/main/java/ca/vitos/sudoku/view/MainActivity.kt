package ca.vitos.sudoku.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ca.vitos.sudoku.R
import ca.vitos.sudoku.view.custom.SudokuAnother
import ca.vitos.sudoku.viewmodel.PlaySudokuViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SudokuAnother.OnToucListener {


    private lateinit var viewModel : PlaySudokuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sudoku.requestFocus()
        sudoku.isClickable = true
        sudoku.registerListener(this)

        sudoku.contentDescription = "Sudoku View"

        viewModel = ViewModelProviders.of(this).get(PlaySudokuViewModel::class.java)
        viewModel.sudokuGame.selectedCellLiveData.observe(this, Observer { updateSelectedCellUI(it) })

        focusHook.setOnFocusChangeListener { view, b ->
            run {

                Log.d("KeyPressTest", "Has Focus Dummy")
                sudoku.requestFocus()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun updateSelectedCellUI(cell: Pair<Int,Int>?) = cell?.let {
        sudoku.updateSelectedCellUI(cell.first,cell.second)
    }
    override fun onCellTouched(row: Int, col: Int) {
        viewModel.sudokuGame.updateSelectedCell(row,col)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return sudoku.handleKeyEvent(event)
    }
}
