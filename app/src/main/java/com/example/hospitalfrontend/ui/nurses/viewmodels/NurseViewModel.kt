package com.example.hospitalfrontend.ui.nurses.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospitalfrontend.model.*
import com.example.hospitalfrontend.network.RemoteViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NurseViewModel() : ViewModel() {
    // Login variables
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> get() = _loginState.asStateFlow()

    private val _nurseState = MutableStateFlow<NurseState?>(null)
    val nurseState: StateFlow<NurseState?> get() = _nurseState.asStateFlow()

    // Variable for a list of nurse
    private val _nurses = MutableStateFlow<List<NurseState>>(listOf())
    val nurses: StateFlow<List<NurseState>> = _nurses

    // List of nurse specialties
    private val _specialityNurse = MutableStateFlow<List<String>>(emptyList())
    val specialityNurse: StateFlow<List<String>> = _specialityNurse

    // Variable for search nurse
    private val _searchState = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> get() = _searchState.asStateFlow()

    init {
        loadSpeciality()
    }

    private fun loadSpeciality() {
        viewModelScope.launch {
            val sortedSpecialities = listOf(
                "Pediatrics Nursing",
                "Cardiology Nursing",
                "Neurology Nursing",
                "Oncology Nursing",
                "Surgical Nursing",
                "Neonatal Nursing",
                "Emergency Nursing",
                "Critical Care Nursing",
                "Psychiatric Nursing",
                "Obstetrics and Gynecology Nursing",
                "Orthopedic Nursing",
                "Anesthesia Nursing",
                "Palliative Care Nursing",
                "Nephrology Nursing",
                "Transplant Nursing",
                "Forensic Nursing",
                "Research Nursing"
            ).sorted()
            _specialityNurse.value = sortedSpecialities
        }
    }

    // Load the list of the Nurse
    fun loadNurses(nurse: List<NurseState>) {
        _nurses.value = nurse
    }

    // Update the value of login
    private fun setLoginState(isLogin: Boolean) {
        _loginState.update { currentState ->
            currentState.copy(isLogin = isLogin)
        }
    }

    fun getLoginState(): Boolean {
        return _loginState.value.isLogin
    }

    fun getNurseState(): NurseState? {
        return _nurseState.value
    }

    // Disconnect Nurse User
    fun disconnectNurse() {
        setLoginState(false)
        _nurseState.value = null
    }

    // Add new Nurse into the list
    fun addNurse(nurse: NurseState) {
        _nurseState.value = nurse
        setLoginState(true)
    }

    // If the data is on data base save the data of response in a variable
    fun loginNurse(nurse: NurseState) {
        setLoginState(true)
        _nurseState.value = nurse
    }

    // Update the search name
    fun updateSearchName(name: String) {
        _searchState.update { it.copy(nurseName = name) }
    }

    // Search nurse by name
    fun findNurseByName() {
        val results =
            // Filter help you to search all the nurse that equals with the name
            _nurses.value.filter { it.name.equals(_searchState.value.nurseName, ignoreCase = true) }

        if (results.isNotEmpty()) {
            _searchState.update {
                it.copy(
                    searchResults = results, resultMessage = "Found ${results.size} nurse(s)."
                )
            }
        } else {
            _searchState.update {
                it.copy(
                    searchResults = emptyList(), resultMessage = "Not Found"
                )
            }
        }
    }
}
