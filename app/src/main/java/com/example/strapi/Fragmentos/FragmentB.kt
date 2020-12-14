package com.example.strapi.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.strapi.R

class FragmentB: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        saveInstantState: Bundle?

    ): View? {

        return inflater.inflate(R.layout.fragment_b,container,false)
    }
}