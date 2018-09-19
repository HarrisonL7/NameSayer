package app.model;

public interface IPractiseListModel {

    Name nextName();

    void playCurrentName();

    boolean hasNext();

    void createUserRecording();

    void keepRecording();

    void compareUserRecording();

}