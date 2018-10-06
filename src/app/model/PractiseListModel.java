package app.model;

import javafx.concurrent.Task;

import java.util.ArrayList;

public class PractiseListModel implements IPractiseListModel{

    private ArrayList<Practisable> _practiseList;
    private Recording _currentUserRecording;
    private NameVersion _currentUserCreatedName;
    private int _currentIndex;
    private boolean _keepRecording;
    private Task compareWorker;

    public PractiseListModel(ArrayList<Practisable> practiseList) {
        _practiseList = practiseList;
        _currentIndex = -1;
    }

    /**
     * Creates a new recording for the currently indexed NameVersion object
     */
    public void createUserRecording() {
        _currentUserRecording = new Recording(_practiseList.get(_currentIndex).toString());
        _currentUserCreatedName = _currentUserRecording.createRecording();
    }

    /**
     * Compares the users production of a name to the database name by
     * starting on a new thread.
     */
    public void compareUserRecording(double volume) {
        if (_currentUserCreatedName == null) { return; }

        compareWorker = compareWorker(volume);
        new Thread(compareWorker).start();

    }

    /**
     * Returns the next Name object in the list. If there is no
     * next name, the current and returned name remains unchanged.
     */
    public Practisable nextName() {
        finaliseRecording();
        if (_currentIndex != _practiseList.size() - 1) {
            _currentIndex++;
        }

        return _practiseList.get(_currentIndex);
    }


    /**
     * Returns the previous Name object in the list. If there is no
     * previous name, the current and returned name remains unchanged.
     */
    public Practisable previousName() {
        finaliseRecording();
        if (_currentIndex != 0) {
            _currentIndex--;
        }

        return _practiseList.get(_currentIndex);
    }

    /**
     * Cancels the recording of the name being currently produced by the
     * user and deletes it.
     */
    public void cancelRecording(){
        _currentUserRecording.cancelRecording();
        _currentUserRecording.deleteRecording();
    }

    /**
     * Creates a new Task which plays the user recording then the
     * database recording
     * @param volume
     */
    private Task compareWorker(double volume) {
        return new Task() {

            @Override
            protected Object call() throws Exception {
                // play user recording
                _currentUserCreatedName.playRecording(volume);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // play database recording
                _practiseList.get(_currentIndex).playRecording(volume);
                return true;
            }
        };

    }

    /**
     * Indicates the user wishes to save their current recording of a name.
     */
    public void keepRecording() {
        _keepRecording = true;
    }

    /**
     * Indicates whether or not there exists a next element by checking the
     * current index
     */
    public boolean hasNext() {
        if (_currentIndex >= _practiseList.size() - 1) {
            return false;
        }
        return true;
    }

    /**
     * Indicates whether or not there exists a previous element by checking the
     * current index.
     */
    public boolean hasPrevious() {
        if (_currentIndex <= 0) {
            return false;
        }
        return true;
    }

    /**
     * Finalises the _currentUserRecording. Involves deleting it if it exists and has not
     * been chosen to be saved by the user, otherwise, it is kept.
     */
    private void finaliseRecording() {
        if (!_keepRecording && _currentUserRecording != null) {
            _currentUserRecording.deleteRecording();
        }
        _currentUserRecording = null;
        _keepRecording = false;
    }

}
