using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerMoveJostick : MonoBehaviour
{
    float horizontalMove = 0f;
    float verticalMove = 0f;
    public Joystick joystick;

    public float runSpeedHorizontal = 2;
    public float runSpeed = 1.25f;
    public float jumpSpeed = 3;

    public bool betterJump = false;

    public SpriteRenderer spriteRender;
    public Animator animator;
    Rigidbody2D rb2D;

    // Start is called before the first frame update
    void Start()
    {
        rb2D = GetComponent<Rigidbody2D>();
    }

    // Update is called once per frame
    void Update()
    {
        horizontalMove = joystick.Horizontal * runSpeedHorizontal;
        transform.position += new Vector3(horizontalMove, 0, 0) * Time.deltaTime * runSpeed;

        if (horizontalMove > 0)
        {
            spriteRender.flipX = false;
            animator.SetBool("Run", true);
        }
        else if (horizontalMove < 0)
        {
            spriteRender.flipX = true;
            animator.SetBool("Run", true);
        }
        else
        {
            animator.SetBool("Run", false);
        }

        // ANIMATIONS
        if (CheckGround.isGrounded == false)
        {
            animator.SetBool("Jump", true);
            animator.SetBool("Run", false);
        }
        if (CheckGround.isGrounded == true)
        {
            animator.SetBool("Jump", false);
        }
    }

    public void jump()
    {
        if (CheckGround.isGrounded)
        {
            rb2D.velocity = new Vector2(rb2D.velocity.x, jumpSpeed);
        }
    }
}
