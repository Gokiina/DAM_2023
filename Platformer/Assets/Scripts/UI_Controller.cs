using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Audio;

public class UI_Controller : MonoBehaviour
{
    public GameObject panelOptions;
    public AudioSource sound;

    public void showOptionsPanel()
    {
        Time.timeScale = 0;
        panelOptions.SetActive(true);
        sound.Play();
    }

    public void hideOptionsPanel()
    {
        Time.timeScale = 1;
        panelOptions.SetActive(false);
        sound.Play();
    }

    public void gotoStartScreen()
    {
        Time.timeScale = 1;
        //SceneManager.LoadScene("Level_Start");
        sound.Play();
    }

    public void quitGame()
    {
        Application.Quit();
        sound.Play();
    }
}
