"""
Reads StoredCredential files and puts it into .env file.
"""

file_dir = 'src/main/resources/secrets/token/StoredCredential'

def main():
    with open(file_dir, 'r') as f, open('.env', 'a') as a:
        lines = f.readlines()
        f.seek(0)
        a.write('\nSTORED_CREDENTIAL="')
        for line in lines:
            a.write(line)
        a.write('"')

if __name__ == "__main__":
    main()