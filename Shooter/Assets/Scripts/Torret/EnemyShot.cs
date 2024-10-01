using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyShot : MonoBehaviour
{
    public GameObject enemy_Bullet;
    public Transform initPoint_Bullet;
    public float speed_Bullet = 100;

    Transform position_Player;
    public bool startShooting = false;

    // Start is called before the first frame update
    private void Start()
    {
        position_Player = GameObject.Find("Player").transform;
        //Invoke("shotToPlayer", 1);
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void shotToPlayer()
    {
        if (startShooting)
        {
            Vector3 directionPlayer = position_Player.position - transform.position;
            GameObject bullet = Instantiate(enemy_Bullet, initPoint_Bullet.position, initPoint_Bullet.rotation);
            bullet.GetComponent<Rigidbody>().AddForce(directionPlayer * speed_Bullet);
            Invoke("shotToPlayer", .3f);
        }
    }
}
