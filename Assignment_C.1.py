from bs4 import BeautifulSoup
import requests
from prettytable import PrettyTable

page = requests.get("http://localhost:8000/products.html")
soup = BeautifulSoup(page.content, 'html.parser')



def display_prettified_html_code():
    print('hello')
    print(soup.prettify())


def retrieve_all_products():
    print(soup.find_all('li', class_='span4'))


def retrive_first_product_price():
    all_products = soup.find_all('li', class_='span4')
    product_one = all_products[0]
    product_one_price = product_one.find("strong")
    print(product_one_price.get_text())
    print(product_one_price.get_text().strip().strip('$'))

products = {}
def lazy_comparator():
    all_products = soup.find_all('li', class_='span4')
    
    for product in all_products:
        products[product.find("p").get_text().strip()] = product.find("strong").get_text().strip().strip('$')
    print (sorted([(v, k) for k, v in products.items()]))

def create_table():
    data = ([(v, k) for v, k in products.items()])
    table = PrettyTable()
    table.field_names = ["Product Name", "Price"]
    for item in data:
        table.add_row(item)
    table.align["Product Name"] = "l" 
    table.align["Price"] = "l"  # Right-align the "Price" column  
    print(table)

if __name__ == '__main__':
    retrive_first_product_price()
    display_prettified_html_code()
    retrieve_all_products()
    lazy_comparator()
    create_table()
