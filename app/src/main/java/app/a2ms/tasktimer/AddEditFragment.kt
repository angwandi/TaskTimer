package app.a2ms.tasktimer

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add_edit.*

private const val TAG = "AddEditFragment"
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TASK = "task"

/**
 * A simple [Fragment]
 * lass.
 * Activities that contain this fragment must implement the
 * [AddEditFragment.OnSavedClicked] interface
 * to handle interaction events.
 * Use the [AddEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AddEditFragment : Fragment() {
    private var task: Task? = null
    private var listener: OnSavedClicked? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate starts")
        super.onCreate(savedInstanceState)
        task = arguments?.getParcelable(ARG_TASK)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView starts")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated starts")
        super.onActivityCreated(savedInstanceState)
        addedit_save.setOnClickListener {
            listener?.onSavedClicked()
        }
    }

    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach starts")
        super.onAttach(context)
        if (context is OnSavedClicked) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnSavedClicked")
        }
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach starts")
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnSavedClicked {
        fun onSavedClicked()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param task The task to be edited, or null to add a new task.
         * @return A new instance of fragment AddEditFragment.
         */
        @JvmStatic
        fun newInstance(task: Task?) =
                AddEditFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_TASK, task)
                    }
                }
    }
}

//fun createFrag(task: Task?) {
//    val args = Bundle()
//    args.putParcelable(ARG_TASK, task)
//    val fragment = AddEditFragment()
//    fragment.arguments = args
//}
//
//fun createFrag2(task: Task?) {
//    val fragment = AddEditFragment().apply {
//        arguments = Bundle().apply {
//            putParcelable(ARG_TASK, task)
//        }
//    }
//}
//
//fun simpler(task: Task?) {
//    val fragment = AddEditFragment.newInstance(task)
//}
