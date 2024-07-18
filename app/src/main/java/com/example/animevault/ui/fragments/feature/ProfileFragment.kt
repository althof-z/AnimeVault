package com.example.animevault.ui.fragments.feature

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import coil.load
import com.example.animevault.databinding.FragmentProfileBinding
import com.nareshchocha.filepickerlibrary.models.PickMediaConfig
import com.nareshchocha.filepickerlibrary.models.PickMediaType
import com.nareshchocha.filepickerlibrary.ui.FilePicker
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    companion object {
        const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
    }
    private val viewModel: ProfileFragmentViewModel by viewModel<ProfileFragmentViewModel> ()
    private lateinit var binding: FragmentProfileBinding

    private val filePickerResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ::handleFilePickerResult,
        )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChoose.setOnClickListener {
            chooseImageDialog()
        }

//        binding.goButton.setOnClickListener { viewModel.applyBlur(blurLevel) }
//
//        // Setup view output image file button
//        binding.seeFileButton.setOnClickListener {
//            viewModel.outputUri?.let { currentUri ->
//                val actionView = Intent(Intent.ACTION_VIEW, currentUri)
//                actionView.resolveActivity(requireContext().packageManager)?.run {
//                    startActivity(actionView)
//                }
//            }
//        }
//
//        // Hookup the Cancel button
//        binding.cancelButton.setOnClickListener { viewModel.cancelWork() }
//
//        viewModel.outputWorkInfos.observe(viewLifecycleOwner, workInfosObserver())
    }

    private fun handleFilePickerResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let {
                binding.ivImage.load(it)
                viewModel.setImageUri(it)
            }
        }
    }


    private fun chooseImageDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ -> openFilePicker() }
            .setNegativeButton("Camera") { _, _ -> openCameraFromLibrary() }
            .show()
    }
    private fun openFilePicker() {
        filePickerResult.launch(
            FilePicker.Builder(requireContext())
                .pickMediaBuild(
                    PickMediaConfig(
                        mPickMediaType = PickMediaType.ImageOnly,
                        allowMultiple = false,
                    ),
                ),
        )
    }

    private fun openCameraFromLibrary() {
        filePickerResult.launch(
            FilePicker.Builder(requireContext())
                .imageCaptureBuild(),
        )
    }

}

//    private fun workInfosObserver(): Observer<List<WorkInfo>> {
//        return Observer { listOfWorkInfo ->
//
//            // Note that these next few lines grab a single WorkInfo if it exists
//            // This code could be in a Transformation in the ViewModel; they are included here
//            // so that the entire process of displaying a WorkInfo is in one location.
//
//            // If there are no matching work info, do nothing
//            if (listOfWorkInfo.isEmpty()) {
//                return@Observer
//            }
//
//            // We only care about the one output status.
//            // Every continuation has only one worker tagged TAG_OUTPUT
//            val workInfo = listOfWorkInfo[0]
//
//            if (workInfo.state.isFinished) {
//                showWorkFinished()
//
//                // Normally this processing, which is not directly related to drawing views on
//                // screen would be in the ViewModel. For simplicity we are keeping it here.
//                val outputImageUri = workInfo.outputData.getString(KEY_IMAGE_URI)
//
//                // If there is an output file show "See File" button
//                if (!outputImageUri.isNullOrEmpty()) {
//                    viewModel.setOutputUri(outputImageUri)
//                    binding.seeFileButton.visibility = View.VISIBLE
//                    binding.ivImage.load(outputImageUri)
//                }
//            } else {
//                showWorkInProgress()
//            }
//        }
//    }
//
//    /**
//     * Shows and hides views for when the Activity is processing an image
//     */
//    private fun showWorkInProgress() {
//        with(binding) {
//            progressBar.visibility = View.VISIBLE
//            cancelButton.visibility = View.VISIBLE
//            goButton.visibility = View.GONE
//            seeFileButton.visibility = View.GONE
//        }
//    }
//
//    /**
//     * Shows and hides views for when the Activity is done processing an image
//     */
//    private fun showWorkFinished() {
//        with(binding) {
//            progressBar.visibility = View.GONE
//            cancelButton.visibility = View.GONE
//            goButton.visibility = View.VISIBLE
//        }
//    }
//
//    private val blurLevel: Int
//        get() =
//            when (binding.radioBlurGroup.checkedRadioButtonId) {
//                R.id.radio_blur_lv_1 -> 1
//                R.id.radio_blur_lv_2 -> 2
//                R.id.radio_blur_lv_3 -> 3
//                else -> 1
//            }
