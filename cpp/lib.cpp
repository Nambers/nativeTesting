#include <iostream>
#include <filesystem>
#include <exception>

extern "C" {
    __declspec(dllexport) void test(const char *a) {
        [[unlikely]] if((long long) a == 1L){
            // just some random stuff to keep the compiler from optimizing the code
            std::cout << "1";
            void * aa = new std::exception_ptr();
            void * b = new std::exception();
            if((long long) aa != ((long long) b) + 5){
                std::cout << "3";
            }
        }else{
            std::cout << "2";
        }
        // use filesystem print current path
        std::cout << std::filesystem::current_path() << std::endl;
        std::cout << a << std::endl;
    }
}