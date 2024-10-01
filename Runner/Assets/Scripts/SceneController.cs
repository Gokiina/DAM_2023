using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class SceneController : MonoBehaviour
{
    public GameObject canvasGameOver;
    public GameObject canvasGameStart;

    // Start is called before the first frame update
    void Start()
    {
        Time.timeScale = 0;
    }

    public void gameOver()
    {
        canvasGameOver.SetActive(true);
        Time.timeScale = 0;
    }

    public void gameReset()
    {
        SceneManager.LoadScene(0);
        canvasGameStart.SetActive(false);
        Time.timeScale = 1;
    }

    public void gameStart()
    {
        canvasGameStart.SetActive(false);
        Time.timeScale = 1;
    }

    public void gameQuit()
    {
        Application.Quit();
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
