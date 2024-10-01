using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour
{
    public static GameManager Instance { get; private set; }
    public TextMeshProUGUI textAmmo;
    public TextMeshProUGUI textHealth;
    public int ammo = 50;
    public int health = 100;

    private void Awake()
    {
        Instance = this;
    }

    private void Start()
    {
        textAmmo.text = ammo.ToString();
        textHealth.text = health.ToString();
    }
    public void checkHealth()
    {
        if (health < 0)
        {
            SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
        }
    }
}
