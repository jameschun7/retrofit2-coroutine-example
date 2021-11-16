package com.retrofit2.coroutine.example

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.retrofit2.coroutine.example.databinding.FragmentApiBinding
import com.retrofit2.coroutine.example.http.respBody.RespCarrier
import com.retrofit2.coroutine.example.http.respBody.RespCarrierTracks
import com.retrofit2.coroutine.example.reference.ApiProvider
import com.retrofit2.coroutine.example.reference.Network
import com.retrofit2.coroutine.example.reference.NetworkCallback

class RetrofitApisFragment : Fragment() {

    private var _binding: FragmentApiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentApiBinding.inflate(inflater, container, false)

        binding.setupSDKBtn.setOnClickListener{
            Network.request(
                ApiProvider.provideApi().getCarriersAsync(),
                success = {
                    for (item in it){
                        addLog(item.toString())
                    }
                },
                error = {
                    addLog(it.toString())
                }
            )
        }

        binding.testBtn.setOnClickListener{
            Network.getListCarrier<List<RespCarrier>>(
                onSuccess = {
                    for (item in it){
                        addLog(item.toString())
                    }
                },
                onError = {
                    addLog(it.toString())
                }
            )
        }

        binding.purchaseBtn.setOnClickListener {
            Network.getCarrierTracks<RespCarrierTracks>(
                onSuccess = {
                    addLog(it.toString())
                },
                onError = {
                    addLog(it.message.toString())
                }
            )
        }

        binding.loginBtn.setOnClickListener{
            Network.request(ApiProvider.provideApi().getCarriersAsync(),
            NetworkCallback<List<RespCarrier>>().apply {
                success = {
                    for (item in it){
                        addLog(item.toString())
                    }
                }
                error = {
                    addLog(it.toString())
                }
            })
        }

        binding.clearLogBtn.setOnClickListener{
            binding.log.text = ""

        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addLog(logText: String) {
        //Log.d(TAG, logText)

        binding.log.text = logText.plus("\n"+binding.log.text)

    }


    companion object {
        private const val TAG = "retrofitExample"
    }
}