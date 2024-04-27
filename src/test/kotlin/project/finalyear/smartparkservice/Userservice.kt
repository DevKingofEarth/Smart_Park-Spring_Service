import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@Service
class UserService {
    private val users = mutableListOf<User>()

    fun signUp(user: User): String {
        if (users.any { it.email == user.email }) {
            return "User already exists"
        }
        users.add(user)
        return "User signed up successfully"
    }

    fun signIn(user: User): String {
        val existingUser = users.find { it.email == user.email && it.password == user.password }
        return if (existingUser != null) {
            "User signed in successfully"
        } else {
            "Invalid credentials"
        }
    }
}

// UserRepository.kt
interface UserRepository {
    fun findByEmail(email: String): User?
    fun save(user: User)
}

// AuthController.kt
@RestController
@RequestMapping("/auth")
class AuthController(private val userService: UserService) {
    @PostMapping("/signup")
    fun signUp(@RequestBody user: User): String {
        return userService.signUp(user)
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody user: User): String {
        return userService.signIn(user)
    }
}
