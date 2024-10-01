using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;
using UnityEngine.SceneManagement;

public class SceneController : MonoBehaviour
{
    public GameObject fruitsCollection;
    public static int fruitsRemaining;

    public TextMeshProUGUI textFruitsTotal;
    public TextMeshProUGUI textFruitsCollected;

    // Start is called before the first frame update
    void Start()
    {
        fruitsRemaining = fruitsCollection.gameObject.transform.childCount;
        textFruitsTotal.text = fruitsRemaining.ToString();
        checkFruitsRemaining();
    }

    public void checkFruitsRemaining()
    {
        textFruitsCollected.text = (int.Parse(textFruitsTotal.text) - fruitsRemaining).ToString();

        if (fruitsRemaining == 0)
        {
            Debug.Log("todas las frutas recogidas");
            SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex + 1);
        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
