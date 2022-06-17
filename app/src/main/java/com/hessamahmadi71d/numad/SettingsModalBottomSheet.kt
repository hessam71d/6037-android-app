package com.hessamahmadi71d.numad

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.switchmaterial.SwitchMaterial


class SettingsModalBottomSheet : BottomSheetDialogFragment() {

    lateinit var switchOnlyNumber: SwitchMaterial
    lateinit var switchAutoSave: SwitchMaterial

    lateinit var tvAbout: TextView

    lateinit var mSettingsChangedListener: SettingsChangedListener


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.modal_bottom_sheet_content, container, false)


    // companion object is equivalent to "static" in java!
    companion object {

        // const is like "final static" in java!
        const val TAG = "SettingsBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvAbout = requireView().findViewById(R.id.tvAbout)

        tvAbout.setOnClickListener {
            dismiss()
            startActivity(Intent(requireActivity(), AboutActivity::class.java))
        }

        switchOnlyNumber =
            requireView().findViewById<View>(R.id.switchOnlyNumberBS) as SwitchMaterial
        switchAutoSave = requireView().findViewById<View>(R.id.switchAutoSave) as SwitchMaterial

        switchOnlyNumber.isChecked = G.sharedPreferences.getBoolean("remove_non_numeric", true)
        switchAutoSave.isChecked = G.sharedPreferences.getBoolean("auto_save", true)

        switchOnlyNumber.setOnCheckedChangeListener { _, isChecked ->

            mSettingsChangedListener.onSettingsChange(
                switchAutoSave.isChecked, switchOnlyNumber.isChecked
            )
        }

        switchAutoSave.setOnCheckedChangeListener { _, isChecked ->

            mSettingsChangedListener.onSettingsChange(
                switchAutoSave.isChecked, switchOnlyNumber.isChecked
            )
        }

    }


    interface SettingsChangedListener {
        fun onSettingsChange(boolSave: Boolean, boolOnlyNumeric: Boolean)
    }

    fun setOnSettingsChangedListener(settingsChangedListener: SettingsChangedListener) {
        mSettingsChangedListener = settingsChangedListener
    }


}