using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Audio;

public class FruitsCollected : MonoBehaviour
{
    // public GameObject collectedAnimation;
    public AudioSource sound;

    private void OnTriggerEnter2D(Collider2D collision)
    {
        GetComponent<SpriteRenderer>().enabled = false;
        //collectedAnimation.SetActive(true);
        gameObject.transform.GetChild(0).gameObject.SetActive(true);
        GetComponent<Collider2D>().enabled = false;
        Destroy(gameObject, 0.5f);
        SceneController.fruitsRemaining--;
        GameObject.Find("controlCenter").GetComponent<SceneController>().checkFruitsRemaining();
        sound.Play();
    }

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
