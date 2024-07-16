package br.com.rodrigoamora.transitorio.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.rodrigoamora.transitorio.databinding.FragmentSobreBinding
import br.com.rodrigoamora.transitorio.ui.activity.MainActivity
import br.com.rodrigoamora.transitorio.util.PackageInfoUtil

class SobreFragment: Fragment() {

    private var _binding: FragmentSobreBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvVersionApp: TextView

    private val mainActivity: MainActivity by lazy {
        activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this._binding = FragmentSobreBinding.inflate(inflater, container, false)
        val root: View = this.binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.initView()
    }

    private fun initView() {
        this.tvVersionApp = this.binding.tvVersionApp
        this.tvVersionApp.text = PackageInfoUtil.getVersionName(this.mainActivity)
    }
}