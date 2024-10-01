using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ObstacleScoreArea : MonoBehaviour
{
    GameObject controlCenter;

    // Start is called before the first frame update
    void Start()
    {
        controlCenter = GameObject.Find("controlCenter");
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        controlCenter.GetComponent<ScoreController>().updateScore();
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
