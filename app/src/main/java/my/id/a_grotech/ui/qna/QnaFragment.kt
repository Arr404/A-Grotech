package my.id.a_grotech.ui.qnas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import my.id.a_grotech.`object`.UserPreference
import my.id.a_grotech.adapter.LoadingStateAdapter
import my.id.a_grotech.adapter.QnasListAdapter
import my.id.a_grotech.databinding.FragmentQnaBinding

class QnasFragment : Fragment() {
    private lateinit var userPreference: UserPreference
    private var _binding: FragmentQnaBinding? = null
    private val qnasViewModel: QnasViewModel by viewModels(
        factoryProducer = { requireActivity().let { ViewModelFactory(it) } }
    )
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQnaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.rvQuote.layoutManager = LinearLayoutManager(context)
        userPreference = UserPreference(context!!)
        getData()
        return root
    }
    private fun getData() {
        val adapter = QnasListAdapter()
        binding.rvQuote.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        val token = "Bearer ${userPreference.getAuth()?.token}"
        qnasViewModel.qnas(token).observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}