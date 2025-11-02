# Recipe App Setup Script for Linux/macOS

#!/bin/bash

echo "🍳 Recipe App Setup Script"
echo "================================"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check Java
echo -e "${YELLOW}Checking Java...${NC}"
if ! command -v java &> /dev/null; then
    echo -e "${RED}Java is not installed${NC}"
    echo "Please install Java 21 or higher"
    exit 1
else
    java -version
fi

# Check Node
echo -e "${YELLOW}Checking Node.js...${NC}"
if ! command -v node &> /dev/null; then
    echo -e "${RED}Node.js is not installed${NC}"
    echo "Please install Node.js 18.x or higher"
    exit 1
else
    node -v
fi

# Check PostgreSQL
echo -e "${YELLOW}Checking PostgreSQL...${NC}"
if ! command -v psql &> /dev/null; then
    echo -e "${YELLOW}PostgreSQL is not installed locally${NC}"
    echo "You can use Docker instead: docker-compose up"
else
    psql --version
fi

# Check Maven
echo -e "${YELLOW}Checking Maven...${NC}"
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}Maven is not installed${NC}"
    echo "Please install Maven 3.9.0 or higher"
    exit 1
else
    mvn -v
fi

# Check Docker
echo -e "${YELLOW}Checking Docker...${NC}"
if ! command -v docker &> /dev/null; then
    echo -e "${YELLOW}Docker is not installed (optional)${NC}"
else
    docker -v
fi

echo ""
echo -e "${GREEN}All checks passed!${NC}"
echo ""
echo "Setup Options:"
echo "1. Quick Start with Docker:"
echo "   docker-compose up -d"
echo ""
echo "2. Manual Setup:"
echo "   Backend:  cd backend && mvn spring-boot:run"
echo "   Frontend: cd frontend && npm install && npm run dev"
echo "   Database: createdb recipe_app"
echo ""
echo "Visit: http://localhost:3000"
