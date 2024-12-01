import requests

# Replace 'YOUR_API_KEY' with your OpenWeatherMap API key
# API_KEY = '989015153a4763590df8530dfd6152d0'
API_KEY = 'bd5e378503939ddaee76f12ad7a97608'
# Function to get the weather data for a given city
def get_weather(city_name):
    base_url = 'http://api.openweathermap.org/data/2.5/weather'
    params = {
        'q': city_name,
        'appid': API_KEY,
        'units': 'metric'  # Use 'imperial' for Fahrenheit
    }

    try:
        response = requests.get(base_url, params=params)
        data = response.json()

        if response.status_code == 200:
            temperature = data['main']['temp']
            wind_speed = data['wind']['speed']
            weather_description = data['weather'][0]['description']
            weather = data['weather'][0]['main']

            print(f'Weather in {city_name}:')
            print(f'Temperature: {temperature}°C')
            print(f'Wind Speed: {wind_speed} m/s')
            print(f'Description: {weather_description}')
            print(f'Weather: {weather}')
        else:
            print(f'Failed to fetch weather data. Status code: {response.status_code}')

    except requests.exceptions.RequestException as e:
        print(f'An error occurred: {e}')

# Input the city name from the user
city = input("Enter the name of the city: ")

# Call the function to get the weather information
get_weather(city)
