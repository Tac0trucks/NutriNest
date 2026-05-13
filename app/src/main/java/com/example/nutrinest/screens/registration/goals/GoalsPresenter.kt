package com.example.nutrinest.screens.registration.goals

class GoalsPresenter : GoalsContract.Presenter {
    private var view: GoalsContract.View? = null

    override fun attachView(view: GoalsContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun handleFinish(selectedGoalId: Int, selectedActivityId: Int) {
        // -1 means nothing was selected in the RadioGroup
        if (selectedGoalId == -1 || selectedActivityId == -1) {
            view?.showValidationError("Please select your goal and activity level")
        } else {
            // Here you would normally save everything to Firestore
            view?.onRegistrationFinished()
        }
    }

    override fun handleBack() {
        view?.navigateBack()
    }
}