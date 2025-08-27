package com.example.jiffy.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jiffy.model.Category
import com.example.jiffy.repositories.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository : FirestoreRepository
): ViewModel() {
    //encapsulation
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories : StateFlow<List<Category>> get() = _categories

    init{
        fetchCategories()
    }
    private fun fetchCategories(){
        viewModelScope.launch{
            repository.getCategoriesFlow()
                .catch {
                    //handle error
                    println("Error in flow")
                }
                .collect{
                    categories ->
                    _categories.value = categories
                    println("categories updated in ViewModel")

                }

        }
    }


}