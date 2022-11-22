package com.brijwel.photopicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.brijwel.photopicker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val galleryAdapter = GalleryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rvGallery.adapter = galleryAdapter
        binding.pickSingle.setOnClickListener {
            pickMedia.launch(getMediaPickType())
        }
        binding.pickMultiple.setOnClickListener {
            pickMultipleMedia.launch(getMediaPickType())
        }
    }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                galleryAdapter.addImage(uri)
            }
        }

    private val pickMultipleMedia = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(
            10// this is optional parameter by default max value will be used
        )
    ) { uris ->
        if (uris.isNotEmpty()) {
            galleryAdapter.addImages(uris)
        }
    }

    private fun getMediaPickType(): PickVisualMediaRequest {
        return when (binding.buttonGroup.checkedRadioButtonId) {
            R.id.imageOnly -> PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            R.id.videoOnly -> PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
            else -> PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
        }
    }
}