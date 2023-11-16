package com.example.marketmind.Adapter.Listeners

import android.view.View
import com.example.marketmind.Model.Contribution

interface ContributionClickListener {
    fun onClick(contribuicao: Contribution, view:View)
}