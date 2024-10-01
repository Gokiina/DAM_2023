using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ObstacleGenerator : MonoBehaviour
{
    public float timeMax = 1;
    private float timeInit = 0;
    public GameObject obstacle;
    public GameObject obstacleOrigin;
    public float high = 0.5f;

    // Start is called before the first frame update
    /*void Start()
    {
        GameObject obstacleNew = Instantiate(obstacle);
        obstacleNew.transform.position = obstacleOrigin.transform.position;

        Destroy(obstacleNew, 5);
    }*/

    // Update is called once per frame
    void Update()
    {
        if (timeInit > timeMax)
        {
            GameObject obstacleNew = Instantiate(obstacle);
            obstacleNew.transform.position = obstacleOrigin.transform.position + new Vector3(0, Random.Range(-high, high), 0);

            Destroy(obstacleNew, 5);
            timeInit = 0;
        }
        else
        {
            timeInit += Time.deltaTime;
        }
    }
}
