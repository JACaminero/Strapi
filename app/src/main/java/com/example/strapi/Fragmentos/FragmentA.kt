package com.example.strapi.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainer
import com.example.strapi.R
import java.time.Instant

class FragmentA: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        saveInstantState: Bundle?

    ): View? {
        
        return inflater.inflate(R.layout.fragment_a,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}